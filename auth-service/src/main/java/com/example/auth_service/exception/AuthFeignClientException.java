package com.example.auth_service.exception;

public class AuthFeignClientException extends RuntimeException {
    public AuthFeignClientException(String message) {
        super(message);
    }
}
