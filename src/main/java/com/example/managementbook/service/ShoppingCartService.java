package com.example.managementbook.service;
import com.example.managementbook.model.dto.ShoppingCartDTO;
import com.example.managementbook.model.entity.ShoppingCart;
import com.example.managementbook.model.entity.Product;
import com.example.managementbook.model.entity.User;
import com.example.managementbook.repository.ShoppingCartRepository;
import com.example.managementbook.repository.ProductRepository;
import com.example.managementbook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;


    public List<ShoppingCartDTO> getAllCartsByUser(Long userId) {
        return shoppingCartRepository.findByUserId(userId).stream()
                .map(cart -> {
                    ShoppingCartDTO dto = new ShoppingCartDTO();
                    dto.setId(cart.getId());
                    dto.setQuantity(cart.getQuantity());
                    dto.setUserId(cart.getUser().getId());
                    dto.setProductId(cart.getProduct().getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }


    public ShoppingCartDTO addProductToCart(ShoppingCartDTO cartDTO) {
        User user = userRepository.findById(cartDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(cartDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        if (product.getStock() < cartDTO.getQuantity()) {
            throw new RuntimeException("Not enough stock");
        }
        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);
        cart.setProduct(product);
        cart.setQuantity(cartDTO.getQuantity());
        cart = shoppingCartRepository.save(cart);

        ShoppingCartDTO dto = new ShoppingCartDTO();
        dto.setId(cart.getId());
        dto.setQuantity(cart.getQuantity());
        dto.setUserId(cart.getUser().getId());
        dto.setProductId(cart.getProduct().getId());
        return dto;
    }


    public ShoppingCartDTO updateQuantity(Long cartId, int quantity) {
        ShoppingCart cart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        if (cart.getProduct().getStock() < quantity) {
            throw new RuntimeException("Not enough stock");
        }
        cart.setQuantity(quantity);
        cart = shoppingCartRepository.save(cart);

        ShoppingCartDTO dto = new ShoppingCartDTO();
        dto.setId(cart.getId());
        dto.setQuantity(cart.getQuantity());
        dto.setUserId(cart.getUser().getId());
        dto.setProductId(cart.getProduct().getId());
        return dto;
    }


    public void deleteProductFromCart(Long cartId) {
        ShoppingCart cart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        shoppingCartRepository.delete(cart);
    }


    public void deleteAllProductsFromCart(Long userId) {
        List<ShoppingCart> carts = shoppingCartRepository.findByUserId(userId);
        shoppingCartRepository.deleteAll(carts);
    }
}