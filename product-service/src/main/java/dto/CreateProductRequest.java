package com.example.productservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(description = "Request body for creating a new product")
public class CreateProductRequest {

    @Schema(
            description = "Product name",
            example = "iPhone 17",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @Schema(
            description = "Product description",
            example = "Smartphone",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank
    @Size(max = 500)
    private String description;

    @Schema(
            description = "Product price",
            example = "80000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull
    @Positive
    private BigDecimal price;

    @Schema(
            description = "Product category",
            example = "Mobile",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank
    @Size(min = 2, max = 50)
    private String category;

    @Schema(
            description = "Available stock quantity",
            example = "25",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull
    @PositiveOrZero
    private Integer stock;

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
}