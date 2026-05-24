package com.swiftpay.ledger.dto;

import java.math.BigDecimal;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class BalanceResponse {
    private BigDecimal balance;
}