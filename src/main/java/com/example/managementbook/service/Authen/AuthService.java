package com.example.managementbook.service.Authen;

import com.example.managementbook.model.dto.UserLoginRequest;
import com.example.managementbook.model.dto.UserLoginResponse;
import org.springframework.stereotype.Component;

@Component
public interface AuthService {
    UserLoginResponse login(UserLoginRequest loginRequest);
}
