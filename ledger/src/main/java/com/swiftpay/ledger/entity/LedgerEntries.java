package com.swiftpay.ledger.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Entity
@Data
public class LedgerEntries {
    @Id
    private UUID ledgerEntryId = UUID.randomUUID();

    private UUID paymentId;
    private UUID accountId;
    private UUID counterpartyAccountId;
    private BigDecimal amount;
    private BigDecimal balance;
    private String currency;

    @Enumerated(EnumType.STRING)
    private Type type;

    @CreationTimestamp
    private Instant createdAt;
}
