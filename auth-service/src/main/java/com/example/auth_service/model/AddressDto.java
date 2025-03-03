package com.example.auth_service.model;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddressDto {
    @NotBlank
    private String street;
    @NotBlank
    private String city;
}
