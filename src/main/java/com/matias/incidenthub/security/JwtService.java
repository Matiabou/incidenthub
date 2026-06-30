package com.matias.incidenthub.security;

import com.matias.incidenthub.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(
                secret.getBytes()
        );
    }

    public String generateToken(
            User user
    ) {

        return Jwts.builder()

                .subject(
                        user.getEmail()
                )

                .issuedAt(
                        new Date()
                )

                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        +
                                        86400000
                        )
                )

                .signWith(
                        getKey()
                )

                .compact();

    }

    public String extractEmail(
            String token
    ) {

        Claims claims =
                Jwts.parser()

                        .verifyWith(
                                getKey()
                        )

                        .build()

                        .parseSignedClaims(
                                token
                        )

                        .getPayload();

        return claims.getSubject();

    }

}