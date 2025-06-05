package com.example.managementbook.service;

import com.example.managementbook.model.dto.RegisterRequest;
import com.example.managementbook.model.dto.RegisterResponse;
import com.example.managementbook.model.entity.Role;
import com.example.managementbook.model.entity.User;
import com.example.managementbook.repository.RoleRepository;
import com.example.managementbook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public RegisterResponse register(RegisterRequest request) {

        User user = new User();
        user.setFullname(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());


        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role USER not found"));
        user.setRoles(new HashSet<>(Set.of(userRole)));


        userRepository.save(user);


        return new RegisterResponse(user.getEmail(), "User registered successfully");
    }
}