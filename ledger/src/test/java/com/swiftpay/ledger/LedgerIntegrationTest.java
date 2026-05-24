package com.swiftpay.ledger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.swiftpay.common.event.PaymentInitiatedEvent;
import com.swiftpay.common.event.PaymentCompletedEvent;
import com.swiftpay.ledger.entity.Accounts;
import com.swiftpay.ledger.repository.AccountsRepository;
import com.swiftpay.ledger.repository.LedgerEntriesRepository;
import com.swiftpay.ledger.service.LedgerTransferService;

@SpringBootTest
@Transactional
class LedgerIntegrationTest {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private LedgerEntriesRepository ledgerEntriesRepository;

    @Autowired
    private LedgerTransferService ledgerTransferService;

    @Test
    void processPaymentPersistsBalancesAndLedgerEntries() {
        Accounts sender = new Accounts();
        sender.setBalance(new BigDecimal("100.00"));
        sender = accountsRepository.save(sender);

        Accounts receiver = new Accounts();
        receiver.setBalance(new BigDecimal("25.00"));
        receiver = accountsRepository.save(receiver);

        PaymentInitiatedEvent event = new PaymentInitiatedEvent(
                UUID.randomUUID(),
                sender.getAccountId(),
                receiver.getAccountId(),
                new BigDecimal("30.00"),
                "USD",
                "PENDING",
                "idem-ledger");

        PaymentCompletedEvent completed = ledgerTransferService.processPayment(event);

        Accounts updatedSender = accountsRepository.findById(sender.getAccountId()).orElseThrow();
        Accounts updatedReceiver = accountsRepository.findById(receiver.getAccountId()).orElseThrow();

        assertEquals("COMPLETED", completed.status());
        assertEquals(new BigDecimal("70.00"), updatedSender.getBalance());
        assertEquals(new BigDecimal("55.00"), updatedReceiver.getBalance());
        assertEquals(2, ledgerEntriesRepository.findByAccountIdOrderByCreatedAtDesc(sender.getAccountId()).size() + ledgerEntriesRepository.findByAccountIdOrderByCreatedAtDesc(receiver.getAccountId()).size());
        assertNotNull(completed.processedAt());
    }
}
