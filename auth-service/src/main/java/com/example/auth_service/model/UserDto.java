package com.example.auth_service.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String username;
    private String role;
    @NotBlank
    private String password;
    @NotBlank
    private AddressDto address;
}
