package com.swiftpay.ledger.entity;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;


@Data
@Entity
public class Accounts {
    @Id
    private UUID accountId = UUID.randomUUID();
    private BigDecimal balance;
}
