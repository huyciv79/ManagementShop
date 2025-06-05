package com.example.managementbook.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserLoginResponse {
    private String username;
    private String typeToken;
    private String accessToken;

}
