package com.example.auth_service.model;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtResponse {
    private String token;
    private String message;
}
