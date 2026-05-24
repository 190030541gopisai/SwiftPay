package com.swiftpay.ledger.dto;

import lombok.Data;

@Data
public class InternalServerErrorResponse {
    private ErrorResponse error;
    private String errorId;
}
