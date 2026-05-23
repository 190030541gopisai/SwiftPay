package com.swiftpay.payment.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swiftpay.payment.gateway.dto.request.PaymentRequest;
import com.swiftpay.payment.gateway.dto.response.PaymentResponse;
import com.swiftpay.payment.gateway.service.PaymentsService;

@Slf4j
@RestController
@RequestMapping("/v1/payments")
public class PaymentsController {

    private PaymentsService paymentsService;

    public PaymentsController(PaymentsService paymentsService) {
        this.paymentsService = paymentsService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody PaymentRequest paymentRequest, @RequestHeader("Idempotency-Key") String idempotencyKey) {
        log.info("Received process POST payment request for senderId={}, receiverId={}, amount={}, idempotencyKey={}", paymentRequest.getSenderId(), paymentRequest.getReceiverId(), paymentRequest.getAmount(), idempotencyKey);
        PaymentResponse response = paymentsService.createPayment(paymentRequest, idempotencyKey);
        log.info("Successfully handled payment creation. Returned response with paymentId={}", response.getPaymentId());
        return ResponseEntity.ok(response);
    }
}
