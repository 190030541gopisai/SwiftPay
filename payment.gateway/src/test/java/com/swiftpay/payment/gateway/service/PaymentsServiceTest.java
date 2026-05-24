package com.swiftpay.payment.gateway.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.swiftpay.payment.gateway.client.LedgerClient;
import com.swiftpay.payment.gateway.dto.request.PaymentRequest;
import com.swiftpay.payment.gateway.dto.response.BalanceResponse;
import com.swiftpay.payment.gateway.entity.Payments;
import com.swiftpay.payment.gateway.exception.DuplicateTransactionException;
import com.swiftpay.payment.gateway.exception.InsufficientBalanceException;
import com.swiftpay.payment.gateway.exception.MissingIdempotencyKeyException;
import com.swiftpay.payment.gateway.publisher.PaymentEventPublisher;
import com.swiftpay.payment.gateway.repository.PaymentsRepository;

@ExtendWith(MockitoExtension.class)
public class PaymentsServiceTest {

    @Mock
    PaymentsRepository paymentsRepository;

    @Mock
    StringRedisTemplate stringRedisTemplate;

    @Mock
    ValueOperations<String, String> valueOperations;

    @Mock
    PaymentEventPublisher paymentEventPublisher;

    @Mock
    LedgerClient ledgerClient;

    @InjectMocks
    PaymentsService paymentsService;

    @BeforeEach
    void setup() {
        Mockito.lenient().when(stringRedisTemplate.opsForValue()).thenReturn(valueOperations);
        ReflectionTestUtils.setField(paymentsService, "idempotencyPrefix", "swiftpay:payment:idempotency:");
    }

    @Test
    void createPayment_successfulFlow() {
        String idempotencyKey = "txn-123";
        String redisKey = "swiftpay:payment:idempotency:" + idempotencyKey;
        PaymentRequest request = mock(PaymentRequest.class);
        UUID senderId = UUID.randomUUID();
        UUID receiverId = UUID.randomUUID();
        when(request.getSenderId()).thenReturn(senderId);
        when(request.getReceiverId()).thenReturn(receiverId);
        when(request.getAmount()).thenReturn(new BigDecimal("10"));
        when(request.getCurrency()).thenReturn("USD");

        when(valueOperations.setIfAbsent(eq(redisKey), eq("IN_PROGRESS"), any(Duration.class))).thenReturn(true);

        BalanceResponse balanceResponse = mock(BalanceResponse.class);
        when(balanceResponse.getBalance()).thenReturn(new BigDecimal("100"));
        when(ledgerClient.getBalance(senderId)).thenReturn(balanceResponse);
        when(ledgerClient.checkAccountExists(receiverId)).thenReturn(true);

        when(paymentsRepository.save(any(Payments.class))).thenAnswer(invocation -> {
            Payments p = invocation.getArgument(0);
            p.setPaymentId(UUID.randomUUID());
            return p;
        });

        var response = paymentsService.createPayment(request, idempotencyKey);

        assertNotNull(response);
        verify(paymentEventPublisher, times(1)).publishPaymentInitiated(any());
        verify(valueOperations, times(1)).set(eq(redisKey), eq("PROCESSED"), any(Duration.class));
    }

    @Test
    void createPayment_missingIdempotencyKey_throws() {
        PaymentRequest request = mock(PaymentRequest.class);
        assertThrows(MissingIdempotencyKeyException.class, () -> paymentsService.createPayment(request, null));
    }

    @Test
    void createPayment_duplicateIdempotency_throws() {
        String idempotencyKey = "txn-dup";
        String redisKey = "swiftpay:payment:idempotency:" + idempotencyKey;

        when(valueOperations.setIfAbsent(eq(redisKey), eq("IN_PROGRESS"), any(Duration.class))).thenReturn(false);
        when(stringRedisTemplate.getExpire(redisKey, java.util.concurrent.TimeUnit.HOURS)).thenReturn(5L);

        PaymentRequest request = mock(PaymentRequest.class);
        assertThrows(DuplicateTransactionException.class, () -> paymentsService.createPayment(request, idempotencyKey));
    }

    @Test
    void createPayment_insufficientBalance_throws() {
        String idempotencyKey = "txn-insuff";
        String redisKey = "swiftpay:payment:idempotency:" + idempotencyKey;

        when(valueOperations.setIfAbsent(eq(redisKey), eq("IN_PROGRESS"), any(Duration.class))).thenReturn(true);

        PaymentRequest request = mock(PaymentRequest.class);
        UUID senderId = UUID.randomUUID();
        UUID receiverId = UUID.randomUUID();
        when(request.getSenderId()).thenReturn(senderId);
        when(request.getReceiverId()).thenReturn(receiverId);
        when(request.getAmount()).thenReturn(new BigDecimal("200"));

        BalanceResponse balanceResponse = mock(BalanceResponse.class);
        when(balanceResponse.getBalance()).thenReturn(new BigDecimal("100"));
        when(ledgerClient.getBalance(senderId)).thenReturn(balanceResponse);
        when(ledgerClient.checkAccountExists(receiverId)).thenReturn(true);

        assertThrows(InsufficientBalanceException.class, () -> paymentsService.createPayment(request, idempotencyKey));
    }
}
