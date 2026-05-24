package com.swiftpay.payment.gateway.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.swiftpay.payment.gateway.client.fallback.LedgerClientFallbackFactory;
import com.swiftpay.payment.gateway.dto.response.BalanceResponse;

@FeignClient(
    name = "ledger-service", 
    url = "${swiftpay.ledger.service.url:http://localhost:8081}",
    fallbackFactory = LedgerClientFallbackFactory.class)
public interface LedgerClient {
    
    @GetMapping("/api/ledger/accounts/{accountId}/balance")
    public BalanceResponse getBalance(@PathVariable("accountId") UUID accountId);

    @GetMapping("/api/ledger/accounts/{accountId}/exists")
    public boolean checkAccountExists(@PathVariable("accountId") UUID accountId);
}
