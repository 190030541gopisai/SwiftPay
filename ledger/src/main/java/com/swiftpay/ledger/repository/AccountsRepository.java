package com.swiftpay.ledger.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swiftpay.ledger.entity.Accounts;

public interface AccountsRepository extends JpaRepository<Accounts, UUID> {
}