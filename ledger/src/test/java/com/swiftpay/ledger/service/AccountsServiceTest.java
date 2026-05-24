package com.swiftpay.ledger.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.swiftpay.ledger.dto.BalanceResponse;
import com.swiftpay.ledger.entity.Accounts;
import com.swiftpay.ledger.exception.ResourceNotFoundException;
import com.swiftpay.ledger.repository.AccountsRepository;

@ExtendWith(MockitoExtension.class)
class AccountsServiceTest {

    @Mock
    private AccountsRepository accountsRepository;

    @InjectMocks
    private AccountsService accountsService;

    @Test
    void checkAccountExistsReturnsRepositoryResult() {
        UUID accountId = UUID.randomUUID();
        when(accountsRepository.existsById(accountId)).thenReturn(true);

        boolean exists = accountsService.checkAccountExists(accountId);

        assertEquals(true, exists);
        verify(accountsRepository).existsById(accountId);
    }

    @Test
    void getBalanceReturnsBalanceResponse() {
        UUID accountId = UUID.randomUUID();
        Accounts account = new Accounts();
        account.setAccountId(accountId);
        account.setBalance(new BigDecimal("125.50"));
        when(accountsRepository.findById(accountId)).thenReturn(Optional.of(account));

        BalanceResponse response = accountsService.getBalance(accountId);

        assertEquals(new BigDecimal("125.50"), response.getBalance());
    }

    @Test
    void getBalanceThrowsWhenAccountMissing() {
        UUID accountId = UUID.randomUUID();
        when(accountsRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> accountsService.getBalance(accountId));
    }
}
