package com.swiftpay.payment.gateway.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.swiftpay.payment.gateway.exception.MissingIdempotencyKeyException;
import com.swiftpay.payment.gateway.client.LedgerClient;
import com.swiftpay.payment.gateway.dto.request.PaymentRequest;
import com.swiftpay.payment.gateway.dto.response.BalanceResponse;
import com.swiftpay.payment.gateway.dto.response.PaymentResponse;
import com.swiftpay.payment.gateway.entity.Payments;
import com.swiftpay.payment.gateway.entity.Status;
import com.swiftpay.common.event.PaymentInitiatedEvent;
import com.swiftpay.payment.gateway.exception.DuplicateTransactionException;
import com.swiftpay.payment.gateway.publisher.PaymentEventPublisher;
import com.swiftpay.payment.gateway.repository.PaymentsRepository;

import org.springframework.data.redis.core.StringRedisTemplate;
import com.swiftpay.payment.gateway.exception.InsufficientBalanceException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PaymentsService {

    private static final Duration IDEMPOTENCY_TTL = Duration.ofHours(24);

    private final PaymentsRepository paymentsRepository;
    private final StringRedisTemplate stringRedisTemplate;
    private final PaymentEventPublisher paymentEventPublisher;
    private final LedgerClient ledgerClient;

    @Value("${swiftpay.redis.idempotency-prefix:swiftpay:payment:idempotency:}")
    private String idempotencyPrefix;

    public PaymentsService(PaymentsRepository paymentsRepository,
            StringRedisTemplate stringRedisTemplate,
            PaymentEventPublisher paymentEventPublisher,
            LedgerClient ledgerClient) {
        this.paymentsRepository = paymentsRepository;
        this.stringRedisTemplate = stringRedisTemplate;
        this.paymentEventPublisher = paymentEventPublisher;
        this.ledgerClient = ledgerClient;
    }

    @Transactional
    public PaymentResponse createPayment(PaymentRequest paymentRequest, String idempotencyKey) {
        log.info("Received request to create payment. senderId: {}, receiverId: {}, amount: {}, currency: {}, idempotencyKey: {}", 
                 paymentRequest.getSenderId(), paymentRequest.getReceiverId(), paymentRequest.getAmount(), paymentRequest.getCurrency(), idempotencyKey);
        // if (idempotencyKey == null || idempotencyKey.isBlank()) {
        //     log.warn("Missing idempotency key for payment request from senderId: {}", paymentRequest.getSenderId());
        //     throw new MissingIdempotencyKeyException("Idempotency-Key header is required");
        // }

        // String redisKey = idempotencyPrefix + idempotencyKey;
        // Boolean accepted = stringRedisTemplate.opsForValue().setIfAbsent(redisKey, "IN_PROGRESS", IDEMPOTENCY_TTL);
        // if (!Boolean.TRUE.equals(accepted)) {
        //     log.warn("Duplicate transaction detected for idempotency key: {}", idempotencyKey);
        //     throw new DuplicateTransactionException("Duplicate transaction detected. Try again after 24 hours");
        // }

        // BalanceResponse senderBalanceResponse = ledgerClient.getBalance(paymentRequest.getSenderId());
        // BigDecimal senderBalance = senderBalanceResponse.getBalance();
        // BigDecimal amount = paymentRequest.getAmount();

        // if (senderBalance.compareTo(amount) < 0) {
        //     log.error("Insufficient balance for senderId: {}. Available: {}, Required: {}", paymentRequest.getSenderId(), senderBalance, amount);
        //     throw new InsufficientBalanceException("Insufficient sender balance");
        // }

        Payments payment = new Payments();
        payment.setSenderId(paymentRequest.getSenderId());
        payment.setReceiverId(paymentRequest.getReceiverId());
        payment.setAmount(paymentRequest.getAmount());
        payment.setCurrency(paymentRequest.getCurrency());
        payment.setStatus(Status.PENDING);

        Payments savedPayment = paymentsRepository.save(payment);
        log.info("Payment saved with PENDING status. paymentId: {}", savedPayment.getPaymentId());

        paymentEventPublisher.publishPaymentInitiated(new PaymentInitiatedEvent(
                savedPayment.getPaymentId(),
                savedPayment.getSenderId(),
                savedPayment.getReceiverId(),
                savedPayment.getAmount(),
                savedPayment.getCurrency(),
                savedPayment.getStatus().name(),
                idempotencyKey));
        log.info("PaymentInitiatedEvent published for paymentId: {}", savedPayment.getPaymentId());

        // stringRedisTemplate.opsForValue().set(redisKey, "PROCESSED", IDEMPOTENCY_TTL);

        return mapToResponse(savedPayment);
    }

    private PaymentResponse mapToResponse(Payments payment) {
        PaymentResponse response = new PaymentResponse();
        response.setPaymentId(payment.getPaymentId());
        response.setStatus(payment.getStatus().name());
        return response;
    }

    public void updatePaymentStatus(UUID paymentId, Status newStatus) {
        log.info("Updating payment status for paymentId: {} to {}", paymentId, newStatus);
        Payments payment = paymentsRepository.findById(paymentId)
                .orElseThrow(() -> {
                    log.error("Payment not found for update. paymentId: {}", paymentId);
                    return new RuntimeException("Payment not found: " + paymentId);
                });
        payment.setStatus(newStatus);
        paymentsRepository.save(payment);
        log.info("Successfully updated payment status for paymentId: {}", paymentId);
    }
}
