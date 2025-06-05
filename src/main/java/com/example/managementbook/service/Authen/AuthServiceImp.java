package com.example.managementbook.service.Authen;

import com.example.managementbook.model.dto.UserLoginRequest;
import com.example.managementbook.model.dto.UserLoginResponse;
import com.example.managementbook.security.UserPrinciple;
import com.example.managementbook.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImp implements AuthService {
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Override
    public UserLoginResponse login(UserLoginRequest loginRequest) {
        Authentication authentication;
        authentication = authenticationProvider.
                authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(),
                                loginRequest.getPassword())
                );
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return UserLoginResponse.builder()
                .username(userPrinciple.getUsername())
                .typeToken("Bearer Token")
                .accessToken(jwtProvider.generateToken(userPrinciple))
        .build();

    }

}
