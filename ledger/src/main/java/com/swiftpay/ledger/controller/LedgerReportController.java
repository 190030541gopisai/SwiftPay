package com.swiftpay.ledger.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swiftpay.ledger.dto.TransactionHistoryResponse;
import com.swiftpay.ledger.entity.LedgerEntries;
import com.swiftpay.ledger.repository.LedgerEntriesRepository;

@RestController
@RequestMapping("/api/ledger")
public class LedgerReportController {

    private final LedgerEntriesRepository ledgerEntriesRepository;

    public LedgerReportController(LedgerEntriesRepository ledgerEntriesRepository) {
        this.ledgerEntriesRepository = ledgerEntriesRepository;
    }

    @GetMapping("/transactions/{userId}")
    public List<TransactionHistoryResponse> getTransactionsByUser(@PathVariable UUID userId) {
        return ledgerEntriesRepository.findByAccountIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private TransactionHistoryResponse toResponse(LedgerEntries entry) {
        return new TransactionHistoryResponse(
                entry.getLedgerEntryId(),
                entry.getPaymentId(),
                entry.getAccountId(),
                entry.getCounterpartyAccountId(),
                entry.getAmount(),
                entry.getBalance(),
                entry.getCurrency(),
                entry.getType(),
                entry.getCreatedAt());
    }
}
