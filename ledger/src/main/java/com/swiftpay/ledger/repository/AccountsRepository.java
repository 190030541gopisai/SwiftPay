package com.swiftpay.ledger.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jakarta.persistence.LockModeType;

import com.swiftpay.ledger.entity.Accounts;

public interface AccountsRepository extends JpaRepository<Accounts, UUID> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select a from Accounts a where a.accountId = :accountId")
	Optional<Accounts> findByAccountIdForUpdate(UUID accountId);
}