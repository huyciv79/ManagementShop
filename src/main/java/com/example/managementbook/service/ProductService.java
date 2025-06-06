package com.example.managementbook.service;
import com.example.managementbook.model.dto.ProductDTO;
import com.example.managementbook.model.entity.Product;
import com.example.managementbook.model.entity.Category;
import com.example.managementbook.repository.ProductRepository;
import com.example.managementbook.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;


//    public List<ProductDTO> getAllProducts() {
//        return productRepository.findAll().stream()
//                .map(product -> {
//                    ProductDTO dto = new ProductDTO();
//                    dto.setId(product.getId());
//                    dto.setName(product.getName());
//                    dto.setPrice(product.getPrice());
//                    dto.setStock(product.getStock());
//                    dto.setStatus(product.getStatus());
//                    dto.setCategoryId(product.getCategory().getId());
//                    return dto;
//                })
//                .collect(Collectors.toList());
//    }


    public ProductDTO createProduct(ProductDTO productDTO) {
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setStatus(true);
        product.setCategory(category);
        product = productRepository.save(product);

        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setStatus(product.getStatus());
        dto.setCategoryId(product.getCategory().getId());
        return dto;
    }


    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setCategory(category);
        product = productRepository.save(product);

        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setStatus(product.getStatus());
        dto.setCategoryId(product.getCategory().getId());
        return dto;
    }


    public ProductDTO changeProductStatus(Long id, Boolean status) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setStatus(status);
        product = productRepository.save(product);
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setStatus(product.getStatus());
        dto.setCategoryId(product.getCategory().getId());
        return dto;
    }
}