package com.swiftpay.ledger.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swiftpay.ledger.entity.Accounts;
import com.swiftpay.ledger.entity.LedgerEntries;
import com.swiftpay.ledger.entity.Type;
import com.swiftpay.common.event.PaymentCompletedEvent;
import com.swiftpay.common.event.PaymentInitiatedEvent;
import com.swiftpay.ledger.exception.InsufficientFundsException;
import com.swiftpay.ledger.exception.ResourceNotFoundException;
import com.swiftpay.ledger.repository.AccountsRepository;
import com.swiftpay.ledger.repository.LedgerEntriesRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LedgerTransferService {

    private final AccountsRepository accountsRepository;
    private final LedgerEntriesRepository ledgerEntriesRepository;

    public LedgerTransferService(AccountsRepository accountsRepository, LedgerEntriesRepository ledgerEntriesRepository) {
        this.accountsRepository = accountsRepository;
        this.ledgerEntriesRepository = ledgerEntriesRepository;
    }

    @Transactional
    public PaymentCompletedEvent processPayment(PaymentInitiatedEvent event) {
        log.info("Processing payment for paymentId: {}, amount: {}, from sender: {} to receiver: {}", 
                 event.paymentId(), event.amount(), event.senderId(), event.receiverId());

        validateAmount(event.amount());

        Accounts sender = accountsRepository.findByAccountIdForUpdate(event.senderId())
                .orElseThrow(() -> {
                    log.error("Sender account not found: {}", event.senderId());
                    return new ResourceNotFoundException("Sender account not found: " + event.senderId());
                });

        Accounts receiver = accountsRepository.findByAccountIdForUpdate(event.receiverId())
                .orElseThrow(() -> {
                    log.error("Receiver account not found: {}", event.receiverId());
                    return new ResourceNotFoundException("Receiver account not found: " + event.receiverId());
                });

        if (sender.getBalance().compareTo(event.amount()) < 0 || sender.getBalance().subtract(event.amount()).compareTo(BigDecimal.ZERO) < 0) {
            log.warn("Insufficient funds for sender {}. Current balance: {}, Required: {}", 
                     sender.getAccountId(), sender.getBalance(), event.amount());
            throw new InsufficientFundsException("Insufficient funds for account: " + sender.getAccountId());
        }

        sender.setBalance(sender.getBalance().subtract(event.amount()));
        receiver.setBalance(receiver.getBalance().add(event.amount()));

        accountsRepository.save(sender);
        accountsRepository.save(receiver);

        ledgerEntriesRepository.save(createEntry(event, sender.getAccountId(), receiver.getAccountId(), Type.DEBIT,
                event.amount(), sender.getBalance()));
        ledgerEntriesRepository.save(createEntry(event, receiver.getAccountId(), sender.getAccountId(), Type.CREDIT,
                event.amount(), receiver.getBalance()));

        log.info("Payment completed successfully for paymentId: {}. Sender new balance: {}, Receiver new balance: {}", 
                 event.paymentId(), sender.getBalance(), receiver.getBalance());

        return new PaymentCompletedEvent(
                event.paymentId(),
                event.senderId(),
                event.receiverId(),
                event.amount(),
                event.currency(),
                "COMPLETED",
                event.idempotencyKey(),
                Instant.now());
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            log.warn("Invalid transfer amount: {}", amount);
            throw new IllegalArgumentException("Transfer amount must be greater than zero");
        }
    }

    private LedgerEntries createEntry(PaymentInitiatedEvent event,
            UUID accountId,
            UUID counterpartyAccountId,
            Type type,
            BigDecimal amount,
            BigDecimal resultingBalance) {
        LedgerEntries entry = new LedgerEntries();
        entry.setPaymentId(event.paymentId());
        entry.setAccountId(accountId);
        entry.setCounterpartyAccountId(counterpartyAccountId);
        entry.setType(type);
        entry.setAmount(amount);
        entry.setBalance(resultingBalance);
        entry.setCurrency(event.currency());
        return entry;
    }
}
