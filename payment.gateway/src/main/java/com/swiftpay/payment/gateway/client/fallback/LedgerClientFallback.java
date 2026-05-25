package com.swiftpay.payment.gateway.client.fallback;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.swiftpay.payment.gateway.client.LedgerClient;
import com.swiftpay.payment.gateway.dto.response.BalanceResponse;
import com.swiftpay.payment.gateway.exception.LedgerUnavailableException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LedgerClientFallback implements LedgerClient{

    @Override
    public BalanceResponse getBalance(UUID userId) {
        log.error("Ledger service is unavailable, fallback triggered for getBalance for user: {}", userId);
        throw new LedgerUnavailableException("Ledger service is currently unavailable to check balance. Please try again later.");
    }

    @Override
    public boolean checkAccountExists(UUID accountId) {
        log.error("Ledger service is unavailable, fallback triggered for checkAccountExists for account: {}", accountId);
        throw new LedgerUnavailableException("Ledger service is currently unavailable to verify account. Please try again later.");
    }
    
}
