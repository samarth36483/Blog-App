package com.springboot.blog.page.service;

import com.springboot.blog.page.dto.LoginDTO;
import com.springboot.blog.page.dto.RegisterDTO;

public interface AuthService {

    String login(LoginDTO loginDTO);
    String register(RegisterDTO registerDTO);
}
