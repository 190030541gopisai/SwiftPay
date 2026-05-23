package com.swiftpay.payment.gateway.publisher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.swiftpay.common.event.PaymentInitiatedEvent;

@Slf4j
@Component
public class PaymentEventPublisher {

    private final KafkaTemplate<String, PaymentInitiatedEvent> kafkaTemplate;
    private final String paymentInitiatedTopic;

    public PaymentEventPublisher(KafkaTemplate<String, PaymentInitiatedEvent> kafkaTemplate,
            @Value("${swiftpay.kafka.topics.payment-initiated:payment-initiated}") String paymentInitiatedTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.paymentInitiatedTopic = paymentInitiatedTopic;
    }

    public void publishPaymentInitiated(PaymentInitiatedEvent event) {
        log.info("Publishing PaymentInitiatedEvent to Kafka topic: {}, for paymentId: {}", paymentInitiatedTopic, event.paymentId());
        kafkaTemplate.send(paymentInitiatedTopic, event.paymentId().toString(), event);
        log.debug("PaymentInitiatedEvent sent successfully for paymentId: {}", event.paymentId());
    }
}
