package com.matias.incidenthub.service;

import com.matias.incidenthub.dto.*;

import com.matias.incidenthub.entity.*;

import com.matias.incidenthub.repository.UserRepository;

import com.matias.incidenthub.security.JwtService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AuthService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;

    private final JwtService jwtService;

    public void register(
            RegisterRequest request
    ) {

        User user =
                User.builder()

                        .email(
                                request.email()
                        )

                        .password(
                                encoder.encode(
                                        request.password()
                                )
                        )

                        .role(
                                Role.USER
                        )

                        .build();

        userRepository.save(
                user
        );

    }

    public AuthResponse login(
            LoginRequest request
    ) {

        User user =
                userRepository
                        .findByEmail(
                                request.email()
                        )

                        .orElseThrow();

        boolean valid =
                encoder.matches(
                        request.password(),
                        user.getPassword()
                );

        if (!valid) {
            throw new RuntimeException(
                    "Invalid credentials"
            );
        }

        return new AuthResponse(jwtService.generateToken(user));

    }

}