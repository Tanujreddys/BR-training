package com.example.productservice.controller;

import com.example.productservice.dto.ProductV2Response;
import com.example.productservice.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v2/products")
@Tag(name = "Products", description = "Product management APIs")
public class ProductV2Controller {

    private final ProductService productService;

    public ProductV2Controller(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @Operation(
            summary = "Get all products - V2",
            description = "Returns the V2 product response with category, stock and status information."
    )
    public List<ProductV2Response> getAllProductsV2() {

        return productService.getAllProductsV2();
    }
}