package com.swiftpay.payment.gateway.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import com.swiftpay.payment.gateway.dto.request.PaymentRequest;
import com.swiftpay.payment.gateway.dto.response.ErrorResponse;
import com.swiftpay.payment.gateway.dto.response.InternalServerErrorResponse;
import com.swiftpay.payment.gateway.dto.response.PaymentResponse;
import com.swiftpay.payment.gateway.service.PaymentsService;

@Slf4j
@RestController
@RequestMapping("/v1/payments")
@Tag(name = "Payments", description = "Payment Gateway API")
public class PaymentsController {

    private PaymentsService paymentsService;

    public PaymentsController(PaymentsService paymentsService) {
        this.paymentsService = paymentsService;
    }

    @Operation(
        summary = "Create a new payment", 
        description = "Initiates a new payment transaction"
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "201",
                description = "Payment successfully created",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PaymentResponse.class)
                )
            ),

            @ApiResponse(
                responseCode = "400",
                description = "Invalid request or missing idempotency key",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            ),

            @ApiResponse(
                responseCode = "404",
                description = "Sender or receiver account not found",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            ),

            @ApiResponse(
                responseCode = "409",
                description = "Duplicate transaction detected",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)  
                )
            ),

            @ApiResponse(
                responseCode = "422",
                description = "Insufficient balance",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            ),

            @ApiResponse(
                responseCode = "503",
                description = "Ledger service unavailable",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = InternalServerErrorResponse.class)                
                )
            ),

            @ApiResponse(
                responseCode = "500",
                description = "Unexpected internal server error",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = InternalServerErrorResponse.class)
                )
            )
    })
    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(
        @Valid @RequestBody PaymentRequest paymentRequest, 
        @Parameter(
            description = "Unique idempotency key",
            required = true,
            example = "txn-123"
        )
        @RequestHeader("Idempotency-Key") String idempotencyKey) {
        log.info("Received process POST payment request for senderId={}, receiverId={}, amount={}, idempotencyKey={}", paymentRequest.getSenderId(), paymentRequest.getReceiverId(), paymentRequest.getAmount(), idempotencyKey);
        PaymentResponse response = paymentsService.createPayment(paymentRequest, idempotencyKey);
        log.info("Successfully handled payment creation. Returned response with paymentId={}", response.getPaymentId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
