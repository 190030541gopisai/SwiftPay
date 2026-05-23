package com.swiftpay.common.event;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record PaymentFailedEvent(
        UUID paymentId,
        UUID senderId,
        UUID receiverId,
        BigDecimal amount,
        String currency,
        String status,
        String idempotencyKey,
        String reason,
        Instant processedAt) {
}
