package com.swiftpay.ledger.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.swiftpay.ledger.dto.BalanceResponse;
import com.swiftpay.ledger.service.AccountsService;

@ExtendWith(MockitoExtension.class)
class AccountsControllerTest {

    @Mock
    private AccountsService accountsService;

    @InjectMocks
    private AccountsController accountsController;

    @Test
    void checkAccountExistsDelegatesToService() {
        UUID accountId = UUID.randomUUID();
        when(accountsService.checkAccountExists(accountId)).thenReturn(true);

        ResponseEntity<Boolean> response = accountsController.checkAccountExists(accountId);

        assertEquals(true, response.getBody());
        verify(accountsService).checkAccountExists(accountId);
    }

    @Test
    void getBalanceDelegatesToService() {
        UUID accountId = UUID.randomUUID();
        BalanceResponse balanceResponse = new BalanceResponse(new BigDecimal("42.10"));
        when(accountsService.getBalance(accountId)).thenReturn(balanceResponse);

        ResponseEntity<BalanceResponse> response = accountsController.getBalance(accountId);

        assertNotNull(response.getBody());
        assertEquals(new BigDecimal("42.10"), response.getBody().getBalance());
        verify(accountsService).getBalance(accountId);
    }
}
