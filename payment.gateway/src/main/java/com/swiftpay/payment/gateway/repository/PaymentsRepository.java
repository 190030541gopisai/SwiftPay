package com.swiftpay.payment.gateway.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swiftpay.payment.gateway.entity.Payments;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments, UUID> {
    
}
