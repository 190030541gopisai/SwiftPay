package com.swiftpay.payment.gateway.client.fallback;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.swiftpay.payment.gateway.client.LedgerClient;
import com.swiftpay.payment.gateway.dto.response.BalanceResponse;

@Component
public class LedgerClientFallback implements LedgerClient{

    @Override
    public BalanceResponse getBalance(UUID userId) {
        BalanceResponse response = new BalanceResponse();
        response.setUserId(userId.toString());
        response.setBalance(new BigDecimal("200.00"));
        return response;
    }
    
}
