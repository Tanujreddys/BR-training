package com.example.userservice.security;

import com.example.userservice.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-expiration}")
    private long accessExpiration;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateAccessToken(User user) {

        Date now = new Date();

        Date expiration = new Date(
                now.getTime() + accessExpiration
        );

        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("userId", user.getId())
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                .claim("tokenType", "access")
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    public String generateRefreshToken(User user) {

        Date now = new Date();

        Date expiration = new Date(
                now.getTime() + refreshExpiration
        );

        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("userId", user.getId())
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                .claim("tokenType", "refresh")
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
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

    public String extractTokenType(String token) {

        return extractAllClaims(token)
                .get("tokenType", String.class);
    }
}