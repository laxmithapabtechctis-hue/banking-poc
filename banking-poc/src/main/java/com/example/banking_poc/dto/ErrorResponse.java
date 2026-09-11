package com.example.banking_poc.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(
        name = "ErrorResponse",
        description = "Standard error response returned by the banking API"
)
public class ErrorResponse {

    @Schema(
            description = "Date and time when the error occurred",
            example = "2026-09-04T15:30:00"
    )
    private LocalDateTime timestamp;

    @Schema(
            description = "HTTP status code",
            example = "404"
    )
    private int status;

    @Schema(
            description = "Short description of the HTTP error",
            example = "Not Found"
    )
    private String error;

    @Schema(
            description = "Detailed error message",
            example = "Customer not found with id: 99"
    )
    private String message;

    public ErrorResponse(
            LocalDateTime timestamp,
            int status,
            String error,
            String message) {

        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}