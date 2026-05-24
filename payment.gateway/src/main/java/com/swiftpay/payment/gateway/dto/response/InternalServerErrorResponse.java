package com.swiftpay.payment.gateway.dto.response;

import lombok.Data;

@Data
public class InternalServerErrorResponse {
    private ErrorResponse error;
    private String errorId;
}
