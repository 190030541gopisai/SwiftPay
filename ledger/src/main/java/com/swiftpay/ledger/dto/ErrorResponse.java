package com.swiftpay.ledger.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Standardized error response payload")
public class ErrorResponse {

    @Schema(description = "ISO-8601 formatted date and time of the error", example = "2026-05-24T12:53:00Z")
    private String timestamp;

    @Schema(description = "HTTP status code", example = "400")
    private int status;

    @Schema(description = "HTTP error definition", example = "Bad Request")
    private String error;

    @Schema(description = "Detailed explanation of the error", example = "Sender account ID is required")
    private String message;

    @Schema(description = "The target API URL path where the error occurred", example = "/api/v1/payments")
    private String path;
}
