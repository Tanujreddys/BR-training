package com.example.productservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "V2 product response")
public class ProductV2Response {

    @Schema(description = "Unique product ID", example = "1")
    private Long id;

    @Schema(description = "Product name", example = "iPhone 17")
    private String name;

    @Schema(description = "Product price", example = "80000")
    private BigDecimal price;

    @Schema(description = "Product category", example = "Mobile")
    private String category;

    @Schema(description = "Available stock quantity", example = "25")
    private Integer stock;

    @Schema(description = "Product status", example = "ACTIVE")
    private String status;

    public ProductV2Response() {
    }

    public ProductV2Response(
            Long id,
            String name,
            BigDecimal price,
            String category,
            Integer stock,
            String status) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.stock = stock;
        this.status = status;
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

    public String getCategory() {
        return category;
    }

    public Integer getStock() {
        return stock;
    }

    public String getStatus() {
        return status;
    }
}