package com.swiftpay.payment.gateway.exception;

public class LedgerUnavailableException extends RuntimeException {
    public LedgerUnavailableException(String message) {
        super(message);
    }
}