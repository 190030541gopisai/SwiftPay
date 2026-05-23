package com.swiftpay.payment.gateway.publisher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.swiftpay.payment.gateway.event.PaymentInitiatedEvent;

@Component
public class PaymentEventPublisher {

    private final KafkaTemplate<String, PaymentInitiatedEvent> kafkaTemplate;
    private final String paymentInitiatedTopic;

    public PaymentEventPublisher(KafkaTemplate<String, PaymentInitiatedEvent> kafkaTemplate,
            @Value("${swiftpay.kafka.topics.payment-initiated:payment.initiated}") String paymentInitiatedTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.paymentInitiatedTopic = paymentInitiatedTopic;
    }

    public void publishPaymentInitiated(PaymentInitiatedEvent event) {
        kafkaTemplate.send(paymentInitiatedTopic, event.paymentId().toString(), event);
    }
}
