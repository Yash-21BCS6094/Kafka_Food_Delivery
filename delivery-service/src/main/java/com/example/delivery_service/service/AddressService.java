package com.example.delivery_service.service;

import com.example.delivery_service.dto.AddressDto;

import java.util.UUID;

public interface AddressService {
    AddressDto updateAddress(UUID addressId, AddressDto addressDTO);
    AddressDto getAddressById(UUID addressId);
    void deleteAddress(UUID addressId);
}
