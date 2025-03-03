package com.example.auth_service.exception.handler;

import com.example.auth_service.exception.AuthFeignClientException;
import com.example.auth_service.exception.InvalidAccessException;
import com.example.auth_service.exception.ServeletException;
import com.example.auth_service.exception.UserDoesNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidAccessException.class)
    protected ResponseEntity<Object> handleAddressNotFoundException(InvalidAccessException ex) {
        CustomError customError = new CustomError(
                HttpStatus.NOT_FOUND,
                Header.API_ERROR.getName(),
                ex.getMessage(),
                false
        );

        return new ResponseEntity<>(customError, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AuthFeignClientException.class)
    protected ResponseEntity<Object> handleAuthFeignClient(AuthFeignClientException ex) {
        CustomError customError = new CustomError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Feign cannot process request.",
                ex.getMessage(),
                false
        );

        return new ResponseEntity<>(customError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(ServeletException.class)
    protected ResponseEntity<Object> handleServletException(ServeletException ex) {
        CustomError customError = new CustomError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Servlet cannot return HEADER context.",
                ex.getMessage(),
                false
        );

        return new ResponseEntity<>(customError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserDoesNotExistsException.class)
    protected ResponseEntity<Object> handleServletException(UserDoesNotExistsException ex) {
        CustomError customError = new CustomError(
                HttpStatus.BAD_REQUEST,
                "User does not exits!",
                ex.getMessage(),
                false
        );

        return new ResponseEntity<>(customError, HttpStatus.BAD_REQUEST);
    }
}
