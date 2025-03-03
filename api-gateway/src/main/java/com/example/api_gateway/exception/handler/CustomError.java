package com.example.api_gateway.exception.handler;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * CustomError class is used to structure error responses consistently.
 * It encapsulates details such as HTTP status, error header, message,
 * and a success flag to indicate the request's outcome.
 * This class helps in providing a well-defined error response format
 * that can be easily understood by clients.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CustomError {

    private final HttpStatus httpStatus;
    private final String header;
    private final String message;
    private final Boolean isSuccess;

    /**
     * Constructs a new CustomError instance.
     * @param httpStatus The HTTP status code associated with the error.
     * @param header     A brief title describing the error.
     * @param message    A detailed explanation of the error.
     * @param isSuccess  Indicates whether the operation was successful (false for errors).
     */
    public CustomError(HttpStatus httpStatus,
                       String header,
                       String message,
                       Boolean isSuccess) {
        this.httpStatus = httpStatus;
        this.header = header;
        this.message = message;
        this.isSuccess = isSuccess;
    }
}
