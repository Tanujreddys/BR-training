package com.example.productservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Product response returned by the API")
public class ProductResponse {

    @Schema(description = "Unique product ID", example = "1")
    private Long id;

    @Schema(description = "Product name", example = "iPhone 17")
    private String name;

    @Schema(description = "Product description", example = "Smartphone")
    private String description;

    @Schema(description = "Product price", example = "80000")
    private BigDecimal price;

    @Schema(description = "Product category", example = "Mobile")
    private String category;

    @Schema(description = "Available stock quantity", example = "25")
    private Integer stock;

    @Schema(description = "Product status", example = "ACTIVE")
    private String status;

    @Schema(
            description = "Product creation date and time",
            example = "2026-09-18T10:30:00"
    )
    private LocalDateTime createdAt;

    @Schema(
            description = "Product last update date and time",
            example = "2026-09-18T10:30:00"
    )
    private LocalDateTime updatedAt;

    public ProductResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}