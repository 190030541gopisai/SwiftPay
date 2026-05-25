package com.swiftpay.payment.gateway.dto.request;

import java.math.BigDecimal;
import java.util.UUID;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequest {

    @Schema(
        description = "Sender account ID", 
        example = "123e4567-e89b-12d3-a456-426614174000", 
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Sender account ID is required")
    private UUID senderId;

    @Schema(
        description = "Receiver account ID", 
        example = "987f6543-e21b-34d5-c678-987654321000", 
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Receiver account ID is required")
    private UUID receiverId;

    @Schema(
        description = "Payment amount", 
        example = "100.00", 
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    @Schema(
        description = "Currency code", 
        example = "USD", 
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Currency is required")
    private String currency;
}
