package com.swiftpay.ledger.entity;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

@Entity
public class LedgerEntries {
    @Id
    private UUID ledgerEntryId = UUID.randomUUID();
    private UUID paymentId;
    private UUID accountId;
    private UUID remarksId;
    private BigDecimal amount;
    private BigDecimal balance;
    private String currency;
    
    @Enumerated(EnumType.STRING)
    private Type type;
}
