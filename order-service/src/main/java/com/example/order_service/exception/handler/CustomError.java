package com.example.order_service.exception.handler;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomError {

    private int statusCode;
    private String statusText;
    private String header;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    private Boolean isSuccess;


    @JsonCreator
    public CustomError(
            @JsonProperty("httpStatus") HttpStatus httpStatus,
            @JsonProperty("header") String header,
            @JsonProperty("message") String message,
            @JsonProperty("isSuccess") Boolean isSuccess
    ) {
        this.statusCode = (httpStatus != null) ? httpStatus.value() : HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.statusText = (httpStatus != null) ? httpStatus.getReasonPhrase() : "Internal Server Error";
        this.header = header;
        this.message = message;
        this.isSuccess = (isSuccess != null) ? isSuccess : false;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusText() {
        return statusText;
    }

    public String getHeader() {
        return header;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

}
