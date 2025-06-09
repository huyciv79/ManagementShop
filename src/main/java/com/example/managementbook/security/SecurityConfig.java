package com.example.managementbook.security;

import com.example.managementbook.security.jwt.CustomAscessDeniedHandler;
import com.example.managementbook.security.jwt.JwtAuthTokenFilter;
import com.example.managementbook.security.jwt.JwtEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtAuthTokenFilter jwtAuthTokenFilter;
    @Autowired
    private CustomAscessDeniedHandler customAscessDeniedHandler;
    @Autowired
    private JwtEntryPoint jwtEntryPoint;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecutiry) throws Exception {
        return httpSecutiry.csrf(AbstractHttpConfigurer::disable).authenticationProvider(authenticationProvider()).authorizeHttpRequests(auth -> {
            auth
                    .requestMatchers("/api/v1/admin/**").hasAuthority("ADMIN")
                    .requestMatchers("/swagger-ui/**" , "/api-docs/**","/api/v1/home","/api/v1/auth/**","/api/v1/register").permitAll()
                    .anyRequest().authenticated();
        }).sessionManagement(auth->auth.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(auth->auth.authenticationEntryPoint(jwtEntryPoint).accessDeniedHandler(customAscessDeniedHandler))
                .addFilterAfter(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class).build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
