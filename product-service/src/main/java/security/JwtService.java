package com.example.productservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }

    public Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValid(String token) {

        try {

            extractAllClaims(token);
            return true;

        } catch (Exception ex) {

            System.out.println(
                    "JWT VALIDATION ERROR: "
                            + ex.getClass().getSimpleName()
                            + " - "
                            + ex.getMessage()
            );

            return false;
        }
    }

    public Long extractUserId(String token) {

        return extractAllClaims(token)
                .get("userId", Long.class);
    }

    public String extractEmail(String token) {

        return extractAllClaims(token)
                .get("email", String.class);
    }

    public String extractRole(String token) {

        return extractAllClaims(token)
                .get("role", String.class);
    }
}