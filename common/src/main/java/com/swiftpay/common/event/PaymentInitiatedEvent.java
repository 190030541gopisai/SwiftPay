package com.swiftpay.common.event;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentInitiatedEvent(
        UUID paymentId,
        UUID senderId,
        UUID receiverId,
        BigDecimal amount,
        String currency,
        String status,
        String idempotencyKey) {
}
