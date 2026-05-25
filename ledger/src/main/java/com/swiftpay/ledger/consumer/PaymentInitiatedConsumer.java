package com.swiftpay.ledger.consumer;

import java.time.Instant;

import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DataAccessException;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.transaction.TransactionException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Component;

import com.swiftpay.common.event.PaymentFailedEvent;
import com.swiftpay.common.event.PaymentInitiatedEvent;
import com.swiftpay.ledger.exception.InsufficientFundsException;
import com.swiftpay.ledger.exception.ResourceNotFoundException;
import com.swiftpay.ledger.publisher.PaymentStatusPublisher;
import com.swiftpay.ledger.service.LedgerTransferService;

@Slf4j
@Component
public class PaymentInitiatedConsumer {

    private final LedgerTransferService ledgerTransferService;
    private final PaymentStatusPublisher paymentStatusPublisher;

    public PaymentInitiatedConsumer(LedgerTransferService ledgerTransferService,
            PaymentStatusPublisher paymentStatusPublisher) {
        this.ledgerTransferService = ledgerTransferService;
        this.paymentStatusPublisher = paymentStatusPublisher;
    }

    @RetryableTopic(
        attempts = "4",
        include = {
                DataAccessException.class,
                TransactionException.class,
                JpaSystemException.class
        }
    )
    @KafkaListener(topics = "payment-initiated", groupId = "ledger-group")
    public void processPayment(PaymentInitiatedEvent event, Acknowledgment acknowledgment) {
        log.info("Received PaymentInitiatedEvent for paymentId: {}, senderId: {}, receiverId: {}, amount: {}", 
                 event.paymentId(), event.senderId(), event.receiverId(), event.amount());
        try {
            paymentStatusPublisher.publishCompleted(ledgerTransferService.processPayment(event));
            acknowledgment.acknowledge();
            log.info("Successfully processed payment for paymentId: {}", event.paymentId());
        } 
        catch (ResourceNotFoundException | InsufficientFundsException ex) {
            log.error("Failed to process payment for paymentId: {}. Reason: {}", event.paymentId(), ex.getMessage(), ex);
            paymentStatusPublisher.publishFailed(new PaymentFailedEvent(
                    event.paymentId(),
                    event.senderId(),
                    event.receiverId(),
                    event.amount(),
                    event.currency(),
                    "FAILED",
                    event.idempotencyKey(),
                    ex.getMessage(),
                    Instant.now()));
            acknowledgment.acknowledge();
            log.info("Published PaymentFailedEvent for paymentId: {}", event.paymentId());
        }
        catch (Exception ex) {
            log.error("EXCEPTION TYPE = {}", ex.getClass().getName(), ex);
            throw ex;
        }
    }
}
