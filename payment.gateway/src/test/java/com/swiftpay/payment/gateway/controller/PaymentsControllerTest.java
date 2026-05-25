package com.swiftpay.payment.gateway.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.swiftpay.payment.gateway.dto.request.PaymentRequest;
import com.swiftpay.payment.gateway.dto.response.PaymentResponse;
import com.swiftpay.payment.gateway.service.PaymentsService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class PaymentsControllerTest {

    @Mock
    private PaymentsService paymentsService;

    @InjectMocks
    private PaymentsController paymentsController;

    @Test
    void createPayment_returnsCreated() {
        PaymentRequest request = new PaymentRequest();
        request.setSenderId(java.util.UUID.randomUUID());
        request.setReceiverId(java.util.UUID.randomUUID());
        request.setAmount(java.math.BigDecimal.TEN);
        request.setCurrency("USD");

        String idempotencyKey = "txn-ctrl-1";

        PaymentResponse response = new PaymentResponse();
        response.setPaymentId(java.util.UUID.randomUUID());
        response.setStatus("PENDING");

        when(paymentsService.createPayment(any(PaymentRequest.class), eq(idempotencyKey))).thenReturn(response);

        ResponseEntity<PaymentResponse> resp = paymentsController.createPayment(request, idempotencyKey);

        verify(paymentsService).createPayment(any(PaymentRequest.class), eq(idempotencyKey));
        assert resp.getStatusCode() == HttpStatus.CREATED;
        assert resp.getBody() != null;
    }
}
