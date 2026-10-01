package com.example.productservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Paginated product response")
public class ProductPageResponse {

    @Schema(description = "List of products")
    private List<ProductResponse> content;

    @Schema(description = "Current page number", example = "0")
    private int page;

    @Schema(description = "Number of products per page", example = "10")
    private int size;

    @Schema(description = "Total number of products", example = "100")
    private long totalElements;

    @Schema(description = "Total number of pages", example = "10")
    private int totalPages;

    public ProductPageResponse(
            List<ProductResponse> content,
            int page,
            int size,
            long totalElements,
            int totalPages) {

        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public List<ProductResponse> getContent() {
        return content;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }
}