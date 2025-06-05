package com.example.managementbook.service;
import com.example.managementbook.model.dto.OrderDTO;
import com.example.managementbook.model.entity.Order;
import com.example.managementbook.model.entity.OrderDetail;
import com.example.managementbook.model.entity.ShoppingCart;
import com.example.managementbook.model.entity.User;
import com.example.managementbook.repository.OrderRepository;
import com.example.managementbook.repository.OrderDetailRepository;
import com.example.managementbook.repository.ShoppingCartRepository;
import com.example.managementbook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private UserRepository userRepository;


    public List<OrderDTO> getAllOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(order -> {
                    OrderDTO dto = new OrderDTO();
                    dto.setId(order.getId());
                    dto.setOrderDate(order.getOrderDate());
                    dto.setReceiveDate(order.getReceiveDate());
                    dto.setStatus(order.getStatus());
                    dto.setUserId(order.getUser().getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }


    public OrderDTO checkout(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<ShoppingCart> carts = shoppingCartRepository.findByUserId(userId);
        if (carts.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDate.now());
        order.setReceiveDate(LocalDate.now().plusDays(7));
        order.setStatus(true);
        order = orderRepository.save(order);

        for (ShoppingCart cart : carts) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(cart.getProduct());
            detail.setQuantity(cart.getQuantity());
            detail.setUnitPrice(cart.getProduct().getPrice());
            orderDetailRepository.save(detail);
            cart.getProduct().setStock(cart.getProduct().getStock() - cart.getQuantity());
        }

        shoppingCartRepository.deleteAll(carts);

        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setReceiveDate(order.getReceiveDate());
        dto.setStatus(order.getStatus());
        dto.setUserId(order.getUser().getId());
        return dto;
    }


    public OrderDTO changeOrderStatus(Long orderId, Boolean status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        order = orderRepository.save(order);

        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setReceiveDate(order.getReceiveDate());
        dto.setStatus(order.getStatus());
        dto.setUserId(order.getUser().getId());
        return dto;
    }
}