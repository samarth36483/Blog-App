package com.springboot.blog.page.service;

import com.springboot.blog.page.dto.LoginDTO;
import com.springboot.blog.page.dto.RegisterDTO;
import com.springboot.blog.page.model.Role;
import com.springboot.blog.page.model.User;
import com.springboot.blog.page.repository.RoleRepository;
import com.springboot.blog.page.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Override
    public String login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getUsernameOremail(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "Login successfull";
    }

    @Override
    public String register(RegisterDTO registerDTO) {
        // add a check to see if username is unique or not
        if(!userRepository.findByUsername(registerDTO.getUsername()).isEmpty()){
            throw new BadCredentialsException("Username already exists");
        }
        // add a check to see if email is unique or not
        if(userRepository.existsByEmail(registerDTO.getEmail())){
            throw new BadCredentialsException("Email already exists");
        }
        User newUser = new User();
        newUser.setName(registerDTO.getName());
        newUser.setEmail(registerDTO.getEmail());
        newUser.setUsername(registerDTO.getUsername());
        newUser.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        newUser.setRoles(roles);
        userRepository.save(newUser);
        return "Registration successful";
    }
}