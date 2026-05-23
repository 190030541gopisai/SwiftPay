package com.swiftpay.payment.gateway.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;

@Data
public class PaymentRequest {
    private UUID senderId;
    private UUID receiverId;
    private BigDecimal amount;
    private String currency;
}
