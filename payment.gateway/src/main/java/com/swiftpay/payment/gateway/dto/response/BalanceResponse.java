package com.swiftpay.payment.gateway.dto.response;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class BalanceResponse {
    private String userId;
    private BigDecimal balance;   
}
