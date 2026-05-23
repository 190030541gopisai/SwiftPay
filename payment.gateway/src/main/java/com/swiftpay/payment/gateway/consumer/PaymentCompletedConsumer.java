package com.swiftpay.payment.gateway.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.swiftpay.common.event.PaymentCompletedEvent;
import com.swiftpay.payment.gateway.entity.Status;
import com.swiftpay.payment.gateway.service.PaymentsService;

@Slf4j
@Component
public class PaymentCompletedConsumer {

    private final PaymentsService paymentsService;

    public PaymentCompletedConsumer(PaymentsService paymentsService) {
        this.paymentsService = paymentsService;
    }
    
    @KafkaListener(topics = "payment-completed", groupId = "payment-group")
    public void consumePaymentCompleted(PaymentCompletedEvent event) {
        log.info("Received PaymentCompletedEvent for paymentId: {}", event.paymentId());
        paymentsService.updatePaymentStatus(event.paymentId(), Status.COMPLETED);
        log.info("Successfully updated status to COMPLETED and consumed PaymentCompletedEvent for paymentId: {}", event.paymentId());
    }
}
