package com.swiftpay.payment.gateway.entity;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;


@Entity
@Data
public class Payments {
    @Id
    private UUID paymentId = UUID.randomUUID();

    private UUID senderId;
    private UUID receiverId;
    private BigDecimal amount;
    private String currency;

    @Enumerated(EnumType.STRING)
    private Status status;
}
