package com.example.userservice.controller;

import com.example.userservice.dto.LoginRequest;
import com.example.userservice.dto.LoginResponse;
import com.example.userservice.dto.RefreshTokenRequest;
import com.example.userservice.dto.RegisterRequest;
import com.example.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String register(
            @Valid @RequestBody RegisterRequest request) {

        userService.registerUser(request);

        return "User registered successfully";
    }

    @PostMapping("/login")
    public LoginResponse login(
            @Valid @RequestBody LoginRequest request) {

        return userService.login(request);
    }

    @PostMapping("/refresh")
    public LoginResponse refresh(
            @Valid @RequestBody RefreshTokenRequest request) {

        return userService.refreshAccessToken(
                request.getRefreshToken()
        );
    }
}