package com.matias.incidenthub.security;

import com.matias.incidenthub.entity.User;

import com.matias.incidenthub.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.*;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class CustomUserDetailsService
        implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {

        User user = userRepository.findByEmail(email).orElseThrow();

        return org.springframework.security.core.userdetails.User
                .withUsername(
                        user.getEmail()
                )
                .password(
                        user.getPassword()
                )
                .authorities(
                        user.getRole().name()
                )
                .build();

    }

}