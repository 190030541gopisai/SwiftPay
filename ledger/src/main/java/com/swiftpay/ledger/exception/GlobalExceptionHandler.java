package com.swiftpay.ledger.exception;

import java.time.Instant;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.swiftpay.ledger.dto.ErrorResponse;
import com.swiftpay.ledger.dto.InternalServerErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex,
            HttpServletRequest request) {
        log.warn("ResourceNotFoundException at {}: {}", request.getRequestURI(), ex.getMessage());
        ErrorResponse body = new ErrorResponse();
        body.setTimestamp(Instant.now().toString());
        body.setStatus(HttpStatus.NOT_FOUND.value());
        body.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
        body.setMessage(ex.getMessage());
        body.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientFunds(InsufficientFundsException ex,
            HttpServletRequest request) {
        log.warn("InsufficientFundsException at {}: {}", request.getRequestURI(), ex.getMessage());
        int statusCode = 422;
        ErrorResponse body = new ErrorResponse();
        body.setTimestamp(Instant.now().toString());
        body.setStatus(statusCode);
        body.setError("Unprocessable Entity");
        body.setMessage(ex.getMessage());
        body.setPath(request.getRequestURI());
        return ResponseEntity.status(statusCode).body(body);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex,
            HttpServletRequest request) {
        log.warn("IllegalArgumentException at {}: {}", request.getRequestURI(), ex.getMessage());
        ErrorResponse body = new ErrorResponse();
        body.setTimestamp(Instant.now().toString());
        body.setStatus(HttpStatus.BAD_REQUEST.value());
        body.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        body.setMessage(ex.getMessage());
        body.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> String.format("%s: %s", fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.joining("; "));

        log.warn("Validation failed at {}: {}", request.getRequestURI(), message);
        ErrorResponse body = new ErrorResponse();
        body.setTimestamp(Instant.now().toString());
        body.setStatus(HttpStatus.BAD_REQUEST.value());
        body.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        body.setMessage(message);
        body.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<InternalServerErrorResponse> handleException(Exception ex, HttpServletRequest request) {
        String errorId = UUID.randomUUID().toString();
        log.error("Unhandled exception (errorId={})", errorId, ex);

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse body = new ErrorResponse();
        body.setTimestamp(Instant.now().toString());
        body.setStatus(status.value());
        body.setError(status.getReasonPhrase());
        body.setMessage("Internal Server Error");
        body.setPath(request.getRequestURI());

        InternalServerErrorResponse internalError = new InternalServerErrorResponse();
        internalError.setError(body);
        internalError.setErrorId(errorId);

        return ResponseEntity.status(status).body(internalError);
    }
}