package com.swiftpay.payment.gateway.dto.response;

import java.util.UUID;

import lombok.Data;

@Data
public class PaymentResponse {
    private UUID paymentId;
    private String status;
}
