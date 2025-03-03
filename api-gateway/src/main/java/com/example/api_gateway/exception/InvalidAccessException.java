package com.example.api_gateway.exception;

/**
 * Custom exception class for handling unauthorized access attempts.
 * This exception is thrown when a user tries to access a protected resource
 * without a valid authentication token or with insufficient permissions.
 * Extends RuntimeException, allowing it to be used as an unchecked exception.
 */

public class InvalidAccessException extends RuntimeException {

    /**
     * Constructs a new InvalidAccessException with the specified detail message.
     * @param message The error message describing the reason for the exception.
     */
    public InvalidAccessException(String message) {
        super(message);
    }
}
