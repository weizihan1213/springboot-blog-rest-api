package com.springboot.blog.service;

import com.springboot.blog.payload.LoginDTO;

public interface AuthService {
    String login(LoginDTO loginDTO);
}
