package com.swiftpay.payment.gateway.dto.response;

import java.util.UUID;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Response details for a processed payment transaction")
public class PaymentResponse {

    @Schema(
        description = "Unique identifier of the processed payment", 
        example = "9afc7711-2e65-4f46-880f-90e632cb6fbb",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private UUID paymentId;

    @Schema(
        description = "Current transaction processing status", 
        example = "SUCCESS", 
        allowableValues = {"PENDING", "SUCCESS", "FAILED"},
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String status;
}
