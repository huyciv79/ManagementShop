package com.example.managementbook.model.dto;

import com.example.managementbook.model.entity.Role;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserCreateResponseDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String phone;
    private String address;
    private Boolean status;
    private String role;
}
