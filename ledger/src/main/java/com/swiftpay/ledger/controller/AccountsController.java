package com.swiftpay.ledger.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swiftpay.ledger.dto.BalanceResponse;
import com.swiftpay.ledger.service.AccountsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/ledger/accounts")
public class AccountsController {

    private final AccountsService accountsService;

    public AccountsController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @GetMapping("/{accountId}/exists")
    public ResponseEntity<Boolean> checkAccountExists(@PathVariable UUID accountId) {
        log.info("Received request to check if account exists: {}", accountId);
        boolean exists = accountsService.checkAccountExists(accountId);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/{accountId}/balance")
    public ResponseEntity<BalanceResponse> getBalance(@PathVariable UUID accountId) {
        log.info("Received request to fetch balance for account: {}", accountId);
        BalanceResponse balance = accountsService.getBalance(accountId);
        return ResponseEntity.ok(balance);
    }
}