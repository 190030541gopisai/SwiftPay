package com.swiftpay.payment.gateway.client.fallback;

import java.util.UUID;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import com.swiftpay.payment.gateway.client.LedgerClient;
import com.swiftpay.payment.gateway.dto.response.BalanceResponse;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LedgerClientFallbackFactory implements FallbackFactory<LedgerClient> {

    private final LedgerClientFallback ledgerClientFallback;

    public LedgerClientFallbackFactory(LedgerClientFallback ledgerClientFallback) {
        this.ledgerClientFallback = ledgerClientFallback;
    }

    @Override
    public LedgerClient create(Throwable cause) {
        return new LedgerClient() {
            @Override
            public BalanceResponse getBalance(UUID accountId) {
                if (cause instanceof FeignException.NotFound) {
                    log.warn("Account not found in ledger service: {}", cause.getMessage());
                    throw (FeignException.NotFound) cause;
                }
                log.error("Ledger service error in getBalance: ", cause);
                return ledgerClientFallback.getBalance(accountId);
            }

            @Override
            public boolean checkAccountExists(UUID accountId) {
                if (cause instanceof FeignException.NotFound) {
                    log.warn("Account not found in ledger service: {}", cause.getMessage());
                    throw (FeignException.NotFound) cause;
                }
                log.error("Ledger service error in checkAccountExists: ", cause);
                return ledgerClientFallback.checkAccountExists(accountId);
            }
        };
    }
}
