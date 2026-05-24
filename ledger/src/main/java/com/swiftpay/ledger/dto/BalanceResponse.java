package com.swiftpay.ledger.dto;

import java.math.BigDecimal;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
@Schema(description = "Response payload containing the current ledger balance")
public class BalanceResponse {

    @Schema(
        description = "The current calculated ledger balance", 
        example = "1250.50", 
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private BigDecimal balance;
}
