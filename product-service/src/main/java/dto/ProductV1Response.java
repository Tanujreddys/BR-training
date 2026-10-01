package com.example.productservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "V1 product response")
public class ProductV1Response {

    @Schema(description = "Unique product ID", example = "1")
    private Long id;

    @Schema(description = "Product name", example = "iPhone 17")
    private String name;

    @Schema(description = "Product price", example = "80000")
    private BigDecimal price;

    public ProductV1Response() {
    }

    public ProductV1Response(Long id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}