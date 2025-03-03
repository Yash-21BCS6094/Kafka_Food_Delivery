package com.example.api_gateway.exception.handler;
import com.example.api_gateway.exception.InvalidAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * GlobalExceptionHandler handles exceptions across the entire application.
 * It provides centralized exception handling for specific error scenarios,
 * ensuring consistent error responses.
 * This class uses @RestControllerAdvice to automatically intercept and handle exceptions
 * thrown from controllers.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles InvalidAccessException, which occurs when a user tries to access
     * a protected resource without proper authorization.
     * @param ex The InvalidAccessException thrown.
     * @return ResponseEntity containing a structured error response.
     */
    @ExceptionHandler(InvalidAccessException.class)
    protected ResponseEntity<Object> handleInvalidAccessException(final InvalidAccessException ex) {
        CustomError customError = new CustomError(
                HttpStatus.UNAUTHORIZED,
                Header.API_ERROR.getName(),
                "Access to protected resource | Provide valid Jwt.",
                false
        );
        return new ResponseEntity<>(customError, HttpStatus.UNAUTHORIZED);
    }
}
