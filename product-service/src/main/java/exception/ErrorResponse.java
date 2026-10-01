package com.example.productservice.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Standard API error response")
public class ErrorResponse {

    @Schema(
            description = "Date and time when the error occurred",
            example = "2026-09-18T10:30:00"
    )
    private LocalDateTime timestamp;

    @Schema(
            description = "HTTP status code",
            example = "400"
    )
    private int status;

    @Schema(
            description = "Error type",
            example = "VALIDATION_ERROR"
    )
    private String error;

    @Schema(
            description = "Detailed error message",
            example = "Invalid product data"
    )
    private String message;

    @Schema(
            description = "API path where the error occurred",
            example = "/api/v1/products"
    )
    private String path;

    public ErrorResponse(
            LocalDateTime timestamp,
            int status,
            String error,
            String message,
            String path) {

        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}