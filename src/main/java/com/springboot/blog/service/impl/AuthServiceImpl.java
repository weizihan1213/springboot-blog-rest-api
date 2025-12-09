package com.springboot.blog.service.impl;

import com.springboot.blog.config.SecurityConfig;
import com.springboot.blog.payload.LoginDTO;
import com.springboot.blog.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.net.PasswordAuthentication;

public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public String login(LoginDTO loginDTO) {
        // Create an in-memory authentication "token" object for spring security validation
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword());

        // Store the "token" in the security context
        SecurityContextHolder.getContext().setAuthentication(token);

        return "User logged in successfully!";
    }
}
