package com.example.order_service.exception.handler;

import com.example.order_service.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    protected ResponseEntity<CustomError> handleOrderNotFoundException(final OrderNotFoundException ex) {
        CustomError customError = new CustomError(
                HttpStatus.BAD_REQUEST,
                Header.VALIDATION_ERROR.getName(),
                ex.getMessage(),
                false
        );

        return new ResponseEntity<>(customError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    protected ResponseEntity<CustomError> handleProductNotFoundException(final ProductNotFoundException ex) {
        CustomError customError = new CustomError(
                HttpStatus.BAD_REQUEST,
                Header.VALIDATION_ERROR.getName(),
                ex.getMessage(),
                false
        );

        return new ResponseEntity<>(customError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<CustomError> handleUserNotFoundException(final UserNotFoundException ex) {
        CustomError customError = new CustomError(
                HttpStatus.BAD_REQUEST,
                Header.VALIDATION_ERROR.getName(),
                ex.getMessage(),
                false
        );

        return new ResponseEntity<>(customError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JwtException.class)
    protected ResponseEntity<CustomError> handleJwtException(final JwtException ex) {
        CustomError customError = new CustomError(
                HttpStatus.UNAUTHORIZED,
                Header.VALIDATION_ERROR.getName(),
                ex.getMessage(),
                false
        );

        return new ResponseEntity<>(customError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidAccessException.class)
    protected ResponseEntity<CustomError> handleJwtException(final InvalidAccessException ex) {
        CustomError customError = new CustomError(
                HttpStatus.UNAUTHORIZED,
                Header.VALIDATION_ERROR.getName(),
                ex.getMessage(),
                false
        );

        return new ResponseEntity<>(customError, HttpStatus.UNAUTHORIZED);
    }

}
