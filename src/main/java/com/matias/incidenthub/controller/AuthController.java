package com.matias.incidenthub.controller;

import com.matias.incidenthub.dto.*;

import com.matias.incidenthub.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Auth")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public void register(

            @RequestBody
            @Valid

            RegisterRequest request

    ) {

        authService.register(
                request
        );

    }

    @Operation(summary = "Login user and return JWT token")
    @PostMapping("/login")
    public AuthResponse login(

            @RequestBody
            @Valid

            LoginRequest request

    ) {
        return authService.login(request);
    }

    @GetMapping("/me")
    public String me(

            Authentication auth

    ) {

        return auth.getName();

    }
}