package com.example.managementbook.controller;

import com.example.managementbook.model.dto.PaginateResponseDTO;
import com.example.managementbook.model.dto.ProductDTO;
import com.example.managementbook.model.entity.Product;
import com.example.managementbook.repository.ProductRepository;
import com.example.managementbook.service.CategoryService;
import com.example.managementbook.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/add/{id}")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO dto) {
        return ResponseEntity.ok(productService.createProduct(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO dto) {
        return ResponseEntity.ok(productService.updateProduct(id, dto));
    }

    @GetMapping("/search")
    public PaginateResponseDTO<Product> searchProducts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "price") String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        Sort sort = Sort.by(sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        Page<Product> productPage = productRepository.findByNameContainingIgnoreCase(keyword, pageable);
        return new PaginateResponseDTO<>(
                productPage.getTotalElements(),
                page,
                pageSize,
                productPage.getContent()
        );
    }

    @GetMapping
    public PaginateResponseDTO<Product> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "price") String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        Sort sort = Sort.by(sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        Page<Product> productPage = productRepository.findAll(pageable);
        return new PaginateResponseDTO<Product>(
                productPage.getTotalElements(),
                page,
                pageSize,
                productPage.getContent()
        );
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<ProductDTO> changeProductStatus(@PathVariable Long id, @RequestParam Boolean status) {
        return ResponseEntity.ok(productService.changeProductStatus(id, status));
    }
}