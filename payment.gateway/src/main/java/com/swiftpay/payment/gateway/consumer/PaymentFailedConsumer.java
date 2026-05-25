package com.swiftpay.payment.gateway.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.orm.jpa.JpaSystemException;
import org.hibernate.TransactionException;
import org.springframework.dao.DataAccessException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
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
    
    @RetryableTopic(
        attempts = "4",
        include = {
                DataAccessException.class,
                TransactionException.class,
                JpaSystemException.class
        }
    )
    @KafkaListener(topics = "payment-failed", groupId = "payment-group")
    public void consumePaymentFailed(PaymentFailedEvent event, Acknowledgment acknowledgment) {
        log.warn("Received PaymentFailedEvent for paymentId: {}. Reason: {}", event.paymentId(), event.reason());
        paymentsService.updatePaymentStatus(event.paymentId(), Status.FAILED);
        acknowledgment.acknowledge();
        log.info("Successfully updated status to FAILED and consumed PaymentFailedEvent for paymentId: {}", event.paymentId());
    }
}
