package com.example.orderservice;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import

@RestController
public class OrderController {

    @GetMapping("/api/orders/{id}")
    public Map getOrder(@PathVariable Long id) {

        RestTemplate restTemplate = new RestTemplate();

        Map user = restTemplate.getForObject(
                "http://localhost:8081/api/users/1",
                Map.class
        );

        return Map.of(
                "orderId", id,
                "product", "Laptop",
                "user", user
        );
    }
}