package com.example.productservice.controller;

import com.example.productservice.dto.CreateProductRequest;
import com.example.productservice.dto.ProductPageResponse;
import com.example.productservice.dto.ProductResponse;
import com.example.productservice.dto.UpdateProductRequest;
import com.example.productservice.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Products", description = "Product APIs")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/public")
    @Operation(
            summary = "Public product endpoint",
            description = "Public endpoint that can be accessed without authentication.",
            tags = {"Public APIs"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Public API accessed successfully"
            )
    })
    public String publicProductEndpoint() {
        return "Public Product API is working";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create a new product",
            description = "Creates a new product with name, description, price, category and stock.",
            tags = {"Product Administration"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Product created successfully",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ProductResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid product data"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Product already exists"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ProductResponse createProduct(

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Product details",
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = CreateProductRequest.class
                            ),
                            examples = @ExampleObject(
                                    name = "Create Product Example",
                                    value = """
                                            {
                                               "name": "iPhone 17",
                                               "description": "Smartphone",
                                               "price": 80000,
                                               "category": "Mobile",
                                               "stock": 25
                                            }
                                            """
                            )
                    )
            )
            @Valid @RequestBody CreateProductRequest request) {

        return productService.createProduct(request);
    }

    @GetMapping
    @Operation(
            summary = "Get all products",
            description = "Returns products with pagination and sorting.",
            tags = {"Product Search"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Products retrieved successfully",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ProductPageResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid pagination or sorting parameters"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ProductPageResponse getAllProducts(

            @Parameter(
                    description = "Page number, starting from 0",
                    example = "0"
            )
            @RequestParam(defaultValue = "0") int page,

            @Parameter(
                    description = "Number of products per page",
                    example = "10"
            )
            @RequestParam(defaultValue = "10") int size,

            @Parameter(
                    description = "Sorting field and direction. Example: price,asc",
                    example = "price,asc"
            )
            @RequestParam(required = false) String sort) {

        Pageable pageable;

        if (sort != null && !sort.isBlank()) {

            String[] sortParts = sort.split(",");

            String field = sortParts[0];

            String direction = sortParts.length > 1
                    ? sortParts[1]
                    : "asc";

            if (direction.equalsIgnoreCase("desc")) {

                pageable = org.springframework.data.domain.PageRequest.of(
                        page,
                        size,
                        org.springframework.data.domain.Sort
                                .by(field)
                                .descending()
                );

            } else {

                pageable = org.springframework.data.domain.PageRequest.of(
                        page,
                        size,
                        org.springframework.data.domain.Sort
                                .by(field)
                                .ascending()
                );
            }

        } else {

            pageable = org.springframework.data.domain.PageRequest.of(
                    page,
                    size
            );
        }

        return productService.getAllProducts(pageable);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get product by ID",
            description = "Returns a single product using its ID.",
            tags = {"Products"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product retrieved successfully",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ProductResponse.class
                            ),
                            examples = @ExampleObject(
                                    name = "Product Response Example",
                                    value = """
                                            {
                                               "id": 1,
                                               "name": "iPhone 17",
                                               "description": "Smartphone",
                                               "price": 80000,
                                               "category": "Mobile",
                                               "stock": 25,
                                               "status": "ACTIVE",
                                               "createdAt": "2026-09-18T10:30:00",
                                               "updatedAt": "2026-09-18T10:30:00"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ProductResponse getProduct(

            @Parameter(
                    description = "Product ID",
                    required = true,
                    example = "1"
            )
            @PathVariable Long id) {

        return productService.getProduct(id);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a product",
            description = "Updates all product details for the given product ID.",
            tags = {"Product Administration"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product updated successfully",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ProductResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid product data"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ProductResponse updateProduct(

            @Parameter(
                    description = "Product ID",
                    required = true,
                    example = "1"
            )
            @PathVariable Long id,

            @Valid @RequestBody UpdateProductRequest request) {

        return productService.updateProduct(id, request);
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Partially update a product",
            description = "Updates selected product fields for the given product ID.",
            tags = {"Product Administration"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product partially updated successfully",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ProductResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid product data"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ProductResponse patchProduct(

            @Parameter(
                    description = "Product ID",
                    required = true,
                    example = "1"
            )
            @PathVariable Long id,

            @RequestBody UpdateProductRequest request) {

        return productService.patchProduct(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete a product",
            description = "Deletes a product using its ID.",
            tags = {"Product Administration"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Product deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public void deleteProduct(

            @Parameter(
                    description = "Product ID",
                    required = true,
                    example = "1"
            )
            @PathVariable Long id) {

        productService.deleteProduct(id);
    }
}