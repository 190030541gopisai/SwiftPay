package com.swiftpay.ledger.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.swiftpay.ledger.entity.Type;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response object containing detailed transaction history logs for the ledger")
public record TransactionHistoryResponse(
        @Schema(description = "Unique identifier for the ledger entry", example = "e3b0c442-98fc-11ee-b9d1-0242ac120002", requiredMode = Schema.RequiredMode.REQUIRED)
        UUID ledgerEntryId,
        
        @Schema(description = "Unique identifier for the associated payment", example = "f47ac10b-58cc-4372-a567-0e02b2c3d479", requiredMode = Schema.RequiredMode.REQUIRED)
        UUID paymentId,
        
        @Schema(description = "Unique identifier of the primary account holder", example = "123e4567-e89b-12d3-a456-426614174000", requiredMode = Schema.RequiredMode.REQUIRED)
        UUID accountId,
        
        @Schema(description = "Unique identifier of the counterparty account involved in the transaction", example = "789v0123-x45y-67z8-b901-234567890123", requiredMode = Schema.RequiredMode.REQUIRED)
        UUID counterpartyAccountId,
        
        @Schema(description = "The transaction amount", example = "250.00", requiredMode = Schema.RequiredMode.REQUIRED)
        BigDecimal amount,
        
        @Schema(description = "The resulting account balance after this ledger entry", example = "1500.50", requiredMode = Schema.RequiredMode.REQUIRED)
        BigDecimal balance,
        
        @Schema(description = "The ISO 4217 three-letter currency code", example = "USD", requiredMode = Schema.RequiredMode.REQUIRED)
        String currency,
        
        @Schema(description = "The type of ledger movement", example = "CREDIT", requiredMode = Schema.RequiredMode.REQUIRED)
        Type type,
        
        @Schema(description = "Timestamp when the ledger entry was created", example = "2026-05-24T15:30:00Z", requiredMode = Schema.RequiredMode.REQUIRED)
        Instant createdAt) {
}
