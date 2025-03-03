package com.example.order_service.exception;

public class InvalidAccessException extends RuntimeException {
    public InvalidAccessException(String message) {
        super(message);
    }
}
