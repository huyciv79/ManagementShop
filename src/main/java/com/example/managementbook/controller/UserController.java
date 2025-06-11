package com.example.managementbook.controller;

import com.example.managementbook.model.dto.PaginateResponseDTO;
import com.example.managementbook.model.dto.UserCreateResponseDTO;
import com.example.managementbook.model.dto.UserDTO;
import com.example.managementbook.model.entity.User;
import com.example.managementbook.repository.UserRepository;
import com.example.managementbook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @PostMapping
    public ResponseEntity<UserCreateResponseDTO> createUser(@RequestBody UserCreateResponseDTO dto) {
        return ResponseEntity.ok(userService.createUser(dto));
    }
    @GetMapping
    public PaginateResponseDTO<User> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "username") String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        Sort sort = Sort.by(sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        Page<User> userPage = userRepository.findAll(pageable);
        return new PaginateResponseDTO<User>(
                userPage.getTotalElements(),
                page,
                pageSize,
                userPage.getContent()
        );
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{userId}/role/{roleId}")
    public ResponseEntity<Void> addRoleUser(@PathVariable Long userId,@PathVariable Long roleId) {
        userService.addRoleUser(userId,roleId);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{userId}/role/{roleId}")
    public ResponseEntity<Void> deleteRoleUser(@PathVariable Long userId,@PathVariable Long roleId) {
        userService.deleteRoleUser(userId,roleId);
        return ResponseEntity.ok().build();
    }

}
