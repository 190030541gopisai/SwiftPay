package com.swiftpay.ledger.exception;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException ex,
            HttpServletRequest request) {
        log.warn("ResourceNotFoundException at {}: {}", request.getRequestURI(), ex.getMessage());
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        body.put("message", ex.getMessage());
        body.put("path", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<Map<String, Object>> handleInsufficientFunds(InsufficientFundsException ex,
            HttpServletRequest request) {
        log.warn("InsufficientFundsException at {}: {}", request.getRequestURI(), ex.getMessage());
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("status", HttpStatus.UNPROCESSABLE_ENTITY.value());
        body.put("error", HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase());
        body.put("message", ex.getMessage());
        body.put("path", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(body);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex,
            HttpServletRequest request) {
        log.warn("IllegalArgumentException at {}: {}", request.getRequestURI(), ex.getMessage());
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        body.put("message", ex.getMessage());
        body.put("path", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex, HttpServletRequest request) {
        String errorId = UUID.randomUUID().toString();
        log.error("Unhandled exception (errorId={})", errorId, ex);

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", "Internal Server Error");
        body.put("errorId", errorId);
        body.put("path", request.getRequestURI());
        return ResponseEntity.status(status).body(body);
    }
}