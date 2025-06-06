package com.example.managementbook.controller;
import com.example.managementbook.model.dto.CategoryDTO;
import com.example.managementbook.model.dto.PaginateResponseDTO;
import com.example.managementbook.model.entity.Category;
import com.example.managementbook.repository.CategoryRepository;
import com.example.managementbook.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;
    @GetMapping
    public PaginateResponseDTO<Category> getCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "name") String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        Sort sort = Sort.by(sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        return new PaginateResponseDTO<Category>(
                categoryPage.getTotalElements(),
                page,
                pageSize,
                categoryPage.getContent()
        );
    }
    @GetMapping("/search")
    public PaginateResponseDTO<Category> searchCategories(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "name") String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        Sort sort = Sort.by(sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        Page<Category> categoryPage = categoryRepository.findByNameContainingIgnoreCase(keyword, pageable);
        return new PaginateResponseDTO<>(
                categoryPage.getTotalElements(),
                page,
                pageSize,
                categoryPage.getContent()
        );
    }
    @PostMapping("/add")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO dto) {
        return ResponseEntity.ok(categoryService.createCategory(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO dto) {
        return ResponseEntity.ok(categoryService.updateCategory(id, dto));
    }

    @PutMapping("status/{id}")
    public ResponseEntity<CategoryDTO> changeCategoryStatus(@PathVariable Long id, @RequestParam Boolean status) {
        return ResponseEntity.ok(categoryService.changeCategoryStatus(id, status));
    }
}