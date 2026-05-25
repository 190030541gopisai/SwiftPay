package com.swiftpay.ledger.controller;

import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swiftpay.ledger.dto.BalanceResponse;
import com.swiftpay.ledger.dto.ErrorResponse;
import com.swiftpay.ledger.dto.InternalServerErrorResponse;
import com.swiftpay.ledger.service.AccountsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/ledger/accounts")
@Tag(name = "Accounts", description = "Ledger Accounts API")
public class AccountsController {

    private final AccountsService accountsService;

    public AccountsController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @Operation(
        summary = "Check if account exists", 
        description = "Checks whether a given account ID exists in the ledger"
)
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200", 
                description = "Successfully checked account existence",
                content = { 
                        @Content(
                                mediaType = "application/json", 
                                schema = @Schema(implementation = Boolean.class)
                        ) 
                }
            ),
            @ApiResponse(
                responseCode = "500", 
                description = "Internal server error",
                content = @Content(
                        mediaType = "application/json", 
                        schema = @Schema(
                                implementation = InternalServerErrorResponse.class
                        )
                )
            )
    })
    @GetMapping("/{accountId}/exists")
    public ResponseEntity<Boolean> checkAccountExists(@PathVariable UUID accountId) {
        log.info("Received request to check if account exists: {}", accountId);
        boolean exists = accountsService.checkAccountExists(accountId);
        return ResponseEntity.ok(exists);
    }

    @Operation(
        summary = "Get account balance", 
        description = "Retrieves the current balance for the specified account ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Balance fetched successfully",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = BalanceResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Account not found",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = InternalServerErrorResponse.class)
                )
        )
    })
    @GetMapping("/{accountId}/balance")
    public ResponseEntity<BalanceResponse> getBalance(@PathVariable UUID accountId) {
        log.info("Received request to fetch balance for account: {}", accountId);
        BalanceResponse balance = accountsService.getBalance(accountId);
        return ResponseEntity.ok(balance);
    }
}