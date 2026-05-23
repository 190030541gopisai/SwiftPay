package com.swiftpay.ledger.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swiftpay.ledger.entity.LedgerEntries;

public interface LedgerEntriesRepository extends JpaRepository<LedgerEntries, UUID> {

    List<LedgerEntries> findByAccountIdOrderByCreatedAtDesc(UUID accountId);
}
