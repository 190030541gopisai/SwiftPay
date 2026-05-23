package com.swiftpay.ledger.publisher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.swiftpay.common.event.PaymentCompletedEvent;
import com.swiftpay.common.event.PaymentFailedEvent;

@Slf4j
@Component
public class PaymentStatusPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String paymentCompletedTopic;
    private final String paymentFailedTopic;

    public PaymentStatusPublisher(
            KafkaTemplate<String, Object> kafkaTemplate,
            @Value("${kafka.topics.payment-completed:payment-completed}") String paymentCompletedTopic,
            @Value("${kafka.topics.payment-failed:payment-failed}") String paymentFailedTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.paymentCompletedTopic = paymentCompletedTopic;
        this.paymentFailedTopic = paymentFailedTopic;
    }

    public void publishCompleted(PaymentCompletedEvent event) {
        log.info("Publishing PaymentCompletedEvent to Kafka topic: {}, for paymentId: {}", paymentCompletedTopic, event.paymentId());
        kafkaTemplate.send(paymentCompletedTopic, event.paymentId().toString(), event);
        log.debug("PaymentCompletedEvent sent successfully for paymentId: {}", event.paymentId());
    }

    public void publishFailed(PaymentFailedEvent event) {
        log.info("Publishing PaymentFailedEvent to Kafka topic: {}, for paymentId: {}. Reason: {}", paymentFailedTopic, event.paymentId(), event.reason());
        kafkaTemplate.send(paymentFailedTopic, event.paymentId().toString(), event);
        log.debug("PaymentFailedEvent sent successfully for paymentId: {}", event.paymentId());
    }
}
