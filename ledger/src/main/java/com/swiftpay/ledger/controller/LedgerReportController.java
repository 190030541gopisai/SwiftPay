package com.swiftpay.ledger.controller;

import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swiftpay.ledger.dto.TransactionHistoryResponse;
import com.swiftpay.ledger.entity.LedgerEntries;
import com.swiftpay.ledger.repository.LedgerEntriesRepository;

@Slf4j
@RestController
@RequestMapping("/api/ledger")
public class LedgerReportController {

    private final LedgerEntriesRepository ledgerEntriesRepository;

    public LedgerReportController(LedgerEntriesRepository ledgerEntriesRepository) {
        this.ledgerEntriesRepository = ledgerEntriesRepository;
    }

    @GetMapping("/transactions/{userId}")
    public List<TransactionHistoryResponse> getTransactionsByUser(@PathVariable UUID userId) {
        log.info("Fetching transaction history for userId: {}", userId);
        List<LedgerEntries> entries = ledgerEntriesRepository.findByAccountIdOrderByCreatedAtDesc(userId);
        log.info("Successfully fetched {} transaction(s) for userId: {}", entries.size(), userId);
        return entries
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
