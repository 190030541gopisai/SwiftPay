package com.swiftpay.payment.gateway;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiftpay.payment.gateway.client.LedgerClient;
import com.swiftpay.payment.gateway.dto.request.PaymentRequest;
import com.swiftpay.payment.gateway.dto.response.BalanceResponse;
import com.swiftpay.payment.gateway.dto.response.PaymentResponse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.junit.jupiter.api.Disabled;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@Disabled("Integration test requires Docker/Testcontainers; enable with -DrunIntegrationTests=true")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaymentGatewayIntegrationTest {

    public static GenericContainer<?> redis;

    static {
        if ("true".equals(System.getProperty("runIntegrationTests"))) {
            redis = new GenericContainer<>(DockerImageName.parse("redis:latest")).withExposedPorts(6379);
            redis.start();
        }
    }

    @DynamicPropertySource
    static void registerRedisProperties(DynamicPropertyRegistry registry) {
        if (redis != null) {
            registry.add("spring.redis.host", redis::getHost);
            registry.add("spring.redis.port", () -> redis.getMappedPort(6379));
        }
    }

    @LocalServerPort
    int port;

    private final org.springframework.web.client.RestTemplate restTemplate = new org.springframework.web.client.RestTemplate();

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LedgerClient ledgerClient;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public LedgerClient ledgerClient() {
            return org.mockito.Mockito.mock(LedgerClient.class);
        }
    }

    @Test
    void fullFlow_createsPayment() throws Exception {
        UUID sender = UUID.randomUUID();
        UUID receiver = UUID.randomUUID();

        BalanceResponse br = new BalanceResponse();
        br.setBalance(new BigDecimal("1000"));

        when(ledgerClient.getBalance(sender)).thenReturn(br);
        when(ledgerClient.checkAccountExists(receiver)).thenReturn(true);

        PaymentRequest req = new PaymentRequest();
        req.setSenderId(sender);
        req.setReceiverId(receiver);
        req.setAmount(new BigDecimal("10"));
        req.setCurrency("USD");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Idempotency-Key", "it-1");

        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(req), headers);

        ResponseEntity<PaymentResponse> resp = restTemplate.postForEntity("http://localhost:" + port + "/v1/payments", entity, PaymentResponse.class);

        assertThat(resp.getStatusCode().value()).isEqualTo(201);
        assertThat(resp.getBody()).isNotNull();
        assertThat(resp.getBody().getPaymentId()).isNotNull();
        assertThat(resp.getBody().getStatus()).isEqualTo("PENDING");
    }
}
