package com.swiftpay.payment.gateway.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.swiftpay.payment.gateway.dto.response.BalanceResponse;

@FeignClient(
    name = "ledger-service", 
    url = "${swiftpay.ledger.service.url:http://localhost:8081}")
public interface LedgerClient {
    @PostMapping("/api/ledger/balance")
    public BalanceResponse getBalance(UUID userId);
}
