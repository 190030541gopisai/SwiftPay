package com.swiftpay.ledger.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.swiftpay.common.event.PaymentCompletedEvent;
import com.swiftpay.common.event.PaymentInitiatedEvent;
import com.swiftpay.ledger.entity.Accounts;
import com.swiftpay.ledger.entity.LedgerEntries;
import com.swiftpay.ledger.exception.InsufficientFundsException;
import com.swiftpay.ledger.exception.ResourceNotFoundException;
import com.swiftpay.ledger.repository.AccountsRepository;
import com.swiftpay.ledger.repository.LedgerEntriesRepository;

@ExtendWith(MockitoExtension.class)
class LedgerTransferServiceTest {

    @Mock
    private AccountsRepository accountsRepository;

    @Mock
    private LedgerEntriesRepository ledgerEntriesRepository;

    @InjectMocks
    private LedgerTransferService ledgerTransferService;

    @Test
    void processPaymentTransfersFundsAndCreatesLedgerEntries() {
        UUID paymentId = UUID.randomUUID();
        UUID senderId = UUID.randomUUID();
        UUID receiverId = UUID.randomUUID();
        PaymentInitiatedEvent event = new PaymentInitiatedEvent(
                paymentId,
                senderId,
                receiverId,
                new BigDecimal("25.00"),
                "USD",
                "PENDING",
                "idem-1");

        Accounts sender = new Accounts();
        sender.setAccountId(senderId);
        sender.setBalance(new BigDecimal("100.00"));

        Accounts receiver = new Accounts();
        receiver.setAccountId(receiverId);
        receiver.setBalance(new BigDecimal("10.00"));

        when(accountsRepository.findByAccountIdForUpdate(senderId)).thenReturn(Optional.of(sender));
        when(accountsRepository.findByAccountIdForUpdate(receiverId)).thenReturn(Optional.of(receiver));
        when(accountsRepository.save(any(Accounts.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(ledgerEntriesRepository.save(any(LedgerEntries.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PaymentCompletedEvent completed = ledgerTransferService.processPayment(event);

        assertEquals("COMPLETED", completed.status());
        assertEquals(new BigDecimal("75.00"), sender.getBalance());
        assertEquals(new BigDecimal("35.00"), receiver.getBalance());
        verify(accountsRepository, times(2)).save(any(Accounts.class));
        verify(ledgerEntriesRepository, times(2)).save(any(LedgerEntries.class));
    }

    @Test
    void processPaymentThrowsWhenAmountInvalid() {
        PaymentInitiatedEvent event = new PaymentInitiatedEvent(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                BigDecimal.ZERO,
                "USD",
                "PENDING",
                "idem-2");

        assertThrows(IllegalArgumentException.class, () -> ledgerTransferService.processPayment(event));
    }

    @Test
    void processPaymentThrowsWhenSenderMissing() {
        UUID senderId = UUID.randomUUID();
        UUID receiverId = UUID.randomUUID();
        PaymentInitiatedEvent event = new PaymentInitiatedEvent(
                UUID.randomUUID(),
                senderId,
                receiverId,
                new BigDecimal("10.00"),
                "USD",
                "PENDING",
                "idem-3");

        Accounts receiver = new Accounts();
        receiver.setAccountId(receiverId);
        receiver.setBalance(new BigDecimal("10.00"));

        when(accountsRepository.findByAccountIdForUpdate(senderId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> ledgerTransferService.processPayment(event));
    }

    @Test
    void processPaymentThrowsWhenInsufficientFunds() {
        UUID senderId = UUID.randomUUID();
        UUID receiverId = UUID.randomUUID();
        PaymentInitiatedEvent event = new PaymentInitiatedEvent(
                UUID.randomUUID(),
                senderId,
                receiverId,
                new BigDecimal("200.00"),
                "USD",
                "PENDING",
                "idem-4");

        Accounts sender = new Accounts();
        sender.setAccountId(senderId);
        sender.setBalance(new BigDecimal("100.00"));

        Accounts receiver = new Accounts();
        receiver.setAccountId(receiverId);
        receiver.setBalance(new BigDecimal("10.00"));

        when(accountsRepository.findByAccountIdForUpdate(senderId)).thenReturn(Optional.of(sender));
        when(accountsRepository.findByAccountIdForUpdate(receiverId)).thenReturn(Optional.of(receiver));

        assertThrows(InsufficientFundsException.class, () -> ledgerTransferService.processPayment(event));
    }
}
