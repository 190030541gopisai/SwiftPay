package com.swiftpay.payment.gateway.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfig {
    
    @Bean
    public NewTopic paymentInitiatedTopic() {
        return new NewTopic("payment-initiated", 1, (short) 1);
    }
}
