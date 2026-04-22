package com.movie.userservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.movie.userservice.dto.LoginRequest;
import com.movie.userservice.dto.LoginResponse;
import com.movie.userservice.dto.RegisterRequest;
import com.movie.userservice.dto.RegisterResponse;
import com.movie.userservice.model.User;
import com.movie.userservice.service.UserAccountService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserAccountService userAccountService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        User user = userAccountService.register(request);

        RegisterResponse response = RegisterResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .message("Register successful")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return userAccountService.login(request)
                .map(user -> ResponseEntity.ok(LoginResponse.builder()
                        .success(true)
                        .message("Login successful")
                        .userId(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .build()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(LoginResponse.builder()
                                .success(false)
                                .message("Invalid credentials")
                                .build()));
    }
}
