package com.example.productservice.config;

import com.example.productservice.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter) {

        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // Public API
                        .requestMatchers(
                                "/api/v1/products/public"
                        ).permitAll()

                        // View products
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/products/**"
                        ).hasAnyRole(
                                "USER",
                                "MANAGER",
                                "ADMIN"
                        )

                        // Create product
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/products/**"
                        ).hasAnyRole(
                                "MANAGER",
                                "ADMIN"
                        )

                        // Update product
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/v1/products/**"
                        ).hasAnyRole(
                                "MANAGER",
                                "ADMIN"
                        )

                        // Partial update product
                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/v1/products/**"
                        ).hasAnyRole(
                                "MANAGER",
                                "ADMIN"
                        )

                        // Delete product
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/v1/products/**"
                        ).hasRole("ADMIN")

                        // Everything else
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form.disable())

                .httpBasic(basic -> basic.disable())

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}