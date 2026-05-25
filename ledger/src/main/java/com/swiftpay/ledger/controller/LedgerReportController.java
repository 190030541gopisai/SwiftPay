package com.swiftpay.ledger.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swiftpay.ledger.dto.InternalServerErrorResponse;
import com.swiftpay.ledger.dto.TransactionHistoryResponse;
import com.swiftpay.ledger.entity.LedgerEntries;
import com.swiftpay.ledger.repository.LedgerEntriesRepository;

@Slf4j
@RestController
@RequestMapping("/api/ledger")
@Tag(
    name = "Ledger Reports", 
    description = "Ledger Reporting API"
)
public class LedgerReportController {

    private final LedgerEntriesRepository ledgerEntriesRepository;

    public LedgerReportController(LedgerEntriesRepository ledgerEntriesRepository) {
        this.ledgerEntriesRepository = ledgerEntriesRepository;
    }

    @Operation(
        summary = "Get transaction history", 
        description = "Retrieves the transaction history for a given user ID"
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200", 
                description = "Successfully retrieved transaction history",
                content = { 
                    @Content(
                        mediaType = "application/json", 
                        array = @ArraySchema(
                            schema = @Schema(
                                implementation = TransactionHistoryResponse.class
                            )
                        )
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
    @GetMapping("/transactions/{userId}")
    public List<TransactionHistoryResponse> getTransactionsByUser(@PathVariable UUID userId) {
        log.info("Fetching transaction history for userId: {}", userId);
        List<LedgerEntries> entries = ledgerEntriesRepository.findByAccountIdOrderByCreatedAtDesc(userId);
        log.info("Successfully fetched {} transaction(s) for userId: {}", entries.size(), userId);
        return entries
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private TransactionHistoryResponse toResponse(LedgerEntries entry) {
        return new TransactionHistoryResponse(
                entry.getLedgerEntryId(),
                entry.getPaymentId(),
                entry.getAccountId(),
                entry.getCounterpartyAccountId(),
                entry.getAmount(),
                entry.getBalance(),
                entry.getCurrency(),
                entry.getType(),
                entry.getCreatedAt());
    }
}
