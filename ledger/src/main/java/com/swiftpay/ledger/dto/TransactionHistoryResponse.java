package com.swiftpay.ledger.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.swiftpay.ledger.entity.Type;

public record TransactionHistoryResponse(
        UUID ledgerEntryId,
        UUID paymentId,
        UUID accountId,
        UUID counterpartyAccountId,
        BigDecimal amount,
        BigDecimal balance,
        String currency,
        Type type,
        Instant createdAt) {
}
