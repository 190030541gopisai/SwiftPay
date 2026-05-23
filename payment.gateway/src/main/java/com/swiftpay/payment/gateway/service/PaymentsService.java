package com.swiftpay.payment.gateway.service;

import java.math.BigDecimal;
import java.time.Duration;

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
import com.swiftpay.payment.gateway.event.PaymentInitiatedEvent;
import com.swiftpay.payment.gateway.exception.DuplicateTransactionException;
import com.swiftpay.payment.gateway.publisher.PaymentEventPublisher;
import com.swiftpay.payment.gateway.repository.PaymentsRepository;

import org.springframework.data.redis.core.StringRedisTemplate;
import com.swiftpay.payment.gateway.exception.InsufficientBalanceException;

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
        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            throw new MissingIdempotencyKeyException("Idempotency-Key header is required");
        }

        String redisKey = idempotencyPrefix + idempotencyKey;
        Boolean accepted = stringRedisTemplate.opsForValue().setIfAbsent(redisKey, "IN_PROGRESS", IDEMPOTENCY_TTL);
        if (!Boolean.TRUE.equals(accepted)) {
            throw new DuplicateTransactionException("Duplicate transaction detected. Try again after 24 hours");
        }

        BalanceResponse senderBalanceResponse = ledgerClient.getBalance(paymentRequest.getSenderId());
        BigDecimal senderBalance = senderBalanceResponse.getBalance();
        BigDecimal amount = paymentRequest.getAmount();

        if (senderBalance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient sender balance");
        }

        Payments payment = new Payments();
        payment.setSenderId(paymentRequest.getSenderId());
        payment.setReceiverId(paymentRequest.getReceiverId());
        payment.setAmount(paymentRequest.getAmount());
        payment.setCurrency(paymentRequest.getCurrency());
        payment.setStatus(Status.PENDING);

        Payments savedPayment = paymentsRepository.save(payment);

        paymentEventPublisher.publishPaymentInitiated(new PaymentInitiatedEvent(
                savedPayment.getPaymentId(),
                savedPayment.getSenderId(),
                savedPayment.getReceiverId(),
                savedPayment.getAmount(),
                savedPayment.getCurrency(),
                savedPayment.getStatus().name(),
                idempotencyKey));

        // stringRedisTemplate.opsForValue().set(redisKey, "PROCESSED", IDEMPOTENCY_TTL);

        return mapToResponse(savedPayment);
    }

    private PaymentResponse mapToResponse(Payments payment) {
        PaymentResponse response = new PaymentResponse();
        response.setPaymentId(payment.getPaymentId());
        response.setStatus(payment.getStatus().name());
        return response;
    }
}
