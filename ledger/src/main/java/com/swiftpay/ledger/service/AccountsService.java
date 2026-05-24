package com.swiftpay.ledger.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.swiftpay.ledger.dto.BalanceResponse;
import com.swiftpay.ledger.entity.Accounts;
import com.swiftpay.ledger.exception.ResourceNotFoundException;
import com.swiftpay.ledger.repository.AccountsRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountsService {

    private final AccountsRepository accountsRepository;

    public AccountsService(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    public boolean checkAccountExists(UUID accountId) {
        log.info("Checking if account exists for accountId: {}", accountId);
        return accountsRepository.existsById(accountId);
    }

    public BalanceResponse getBalance(UUID accountId) {
        log.info("Fetching balance for accountId: {}", accountId);
        Accounts account = accountsRepository.findById(accountId)
                .orElseThrow(() -> {
                    log.warn("Account not found: {}", accountId);
                    return new ResourceNotFoundException("Account not found: " + accountId);
                });
        
        return new BalanceResponse(account.getBalance());
    }
}