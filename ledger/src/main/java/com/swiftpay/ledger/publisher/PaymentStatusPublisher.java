package com.swiftpay.ledger.publisher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.swiftpay.common.event.PaymentCompletedEvent;
import com.swiftpay.common.event.PaymentFailedEvent;

@Component
public class PaymentStatusPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String paymentCompletedTopic;
    private final String paymentFailedTopic;

    public PaymentStatusPublisher(
            KafkaTemplate<String, Object> kafkaTemplate,
            @Value("payment-completed") String paymentCompletedTopic,
            @Value("payment-failed") String paymentFailedTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.paymentCompletedTopic = paymentCompletedTopic;
        this.paymentFailedTopic = paymentFailedTopic;
    }

    public void publishCompleted(PaymentCompletedEvent event) {
        kafkaTemplate.send(paymentCompletedTopic, event.paymentId().toString(), event);
    }

    public void publishFailed(PaymentFailedEvent event) {
        kafkaTemplate.send(paymentFailedTopic, event.paymentId().toString(), event);
    }
}
