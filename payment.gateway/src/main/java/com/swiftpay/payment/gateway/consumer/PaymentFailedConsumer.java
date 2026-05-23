package com.swiftpay.payment.gateway.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.swiftpay.common.event.PaymentFailedEvent;
import com.swiftpay.payment.gateway.entity.Status;
import com.swiftpay.payment.gateway.service.PaymentsService;

@Slf4j
@Component
public class PaymentFailedConsumer {

    private final PaymentsService paymentsService;

    public PaymentFailedConsumer(PaymentsService paymentsService) {
        this.paymentsService = paymentsService;
    }
    
    @KafkaListener(topics = "payment-failed", groupId = "payment-group")
    public void consumePaymentFailed(PaymentFailedEvent event) {
        log.warn("Received PaymentFailedEvent for paymentId: {}. Reason: {}", event.paymentId(), event.reason());
        paymentsService.updatePaymentStatus(event.paymentId(), Status.FAILED);
        log.info("Successfully updated status to FAILED and consumed PaymentFailedEvent for paymentId: {}", event.paymentId());
    }
}
