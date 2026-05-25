package com.swiftpay.common.event;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record PaymentCompletedEvent(
        UUID paymentId,
        UUID senderId,
        UUID receiverId,
        BigDecimal amount,
        String currency,
        String status,
        String idempotencyKey,
        Instant processedAt) {
}
