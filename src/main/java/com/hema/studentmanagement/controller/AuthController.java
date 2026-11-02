package com.hema.studentmanagement.controller;

import com.hema.studentmanagement.dto.LoginRequest;
import com.hema.studentmanagement.dto.LoginResponse;
import com.hema.studentmanagement.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.hema.studentmanagement.service.UserService;
import com.hema.studentmanagement.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        User user = userService.findByUsername(request.getUsername()).orElse(null);

        if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            String token = jwtUtil.generateToken(user.getUsername());
            return new LoginResponse(token);
        }

        return new LoginResponse("Invalid username or password");
    }
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.saveUser(user);
    }
    @Autowired
    private PasswordEncoder passwordEncoder;
}