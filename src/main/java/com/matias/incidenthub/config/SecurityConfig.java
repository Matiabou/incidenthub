package com.matias.incidenthub.config;

import com.matias.incidenthub.security.JwtFilter;

import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.*;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http
        .SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt
        .BCryptPasswordEncoder;

import org.springframework.security.web.*;

import org.springframework.security.web.authentication
        .UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http
    )

            throws Exception {

        http

                .csrf(
                        csrf -> csrf.disable()
                )

                .authorizeHttpRequests(

                        auth -> auth

                                .requestMatchers(
                                        "/auth/**",
                                        "/health",
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**"
                                )

                                .permitAll()

                                .anyRequest()

                                .authenticated()

                )

                .exceptionHandling(

                        ex -> ex

                                .authenticationEntryPoint(

                                        (
                                                request,
                                                response,
                                                authException
                                        )

                                                -> response.sendError(
                                                HttpServletResponse.SC_UNAUTHORIZED
                                        )

                                )

                )

                .sessionManagement(

                        session ->

                                session.sessionCreationPolicy(
                                        SessionCreationPolicy.STATELESS
                                )

                )

                .addFilterBefore(

                        jwtFilter,

                        UsernamePasswordAuthenticationFilter.class

                );

        return http.build();

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of("*")
        );

        configuration.setAllowedMethods(
                List.of("*")
        );

        configuration.setAllowedHeaders(
                List.of("*")
        );

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }

}