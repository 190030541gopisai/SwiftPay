package com.swiftpay.payment.gateway.dto.response;

import java.math.BigDecimal;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Response details containing the account balance")
public class BalanceResponse {

    @Schema(
        description = "The current available balance of the account", 
        example = "1250.45", 
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private BigDecimal balance;   
}
