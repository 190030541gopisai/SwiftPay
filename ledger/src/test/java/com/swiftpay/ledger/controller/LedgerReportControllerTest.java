package com.swiftpay.ledger.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.swiftpay.ledger.dto.TransactionHistoryResponse;
import com.swiftpay.ledger.entity.LedgerEntries;
import com.swiftpay.ledger.entity.Type;
import com.swiftpay.ledger.repository.LedgerEntriesRepository;

@ExtendWith(MockitoExtension.class)
class LedgerReportControllerTest {

    @Mock
    private LedgerEntriesRepository ledgerEntriesRepository;

    @InjectMocks
    private LedgerReportController ledgerReportController;

    @Test
    void getTransactionsByUserMapsEntriesToResponses() {
        UUID userId = UUID.randomUUID();
        LedgerEntries entry = new LedgerEntries();
        entry.setLedgerEntryId(UUID.randomUUID());
        entry.setPaymentId(UUID.randomUUID());
        entry.setAccountId(userId);
        entry.setCounterpartyAccountId(UUID.randomUUID());
        entry.setAmount(new BigDecimal("15.50"));
        entry.setBalance(new BigDecimal("84.50"));
        entry.setCurrency("USD");
        entry.setType(Type.CREDIT);
        entry.setCreatedAt(Instant.now());

        when(ledgerEntriesRepository.findByAccountIdOrderByCreatedAtDesc(userId)).thenReturn(List.of(entry));

        List<TransactionHistoryResponse> responses = ledgerReportController.getTransactionsByUser(userId);

        assertEquals(1, responses.size());
        assertNotNull(responses.get(0).ledgerEntryId());
        assertEquals(Type.CREDIT, responses.get(0).type());
    }
}
