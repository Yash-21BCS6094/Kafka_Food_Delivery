package com.example.delivery_service.controller;

import com.example.delivery_service.dto.AddressDto;
import com.example.delivery_service.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
@RestController
@RequestMapping("/api/v1/users/address")
public class AddressController {

    @Autowired
    private final AddressService addressService;

    public AddressController(AddressService addressService){
        this.addressService = addressService;
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressDto> updateAddress(@PathVariable UUID addressId,
                                                    @RequestBody AddressDto addressDTO,
                                                    @RequestHeader("X-User-Role") String userRole) {
        if(userRole.equalsIgnoreCase("USER")){
            AddressDto add = addressService.updateAddress(addressId, addressDTO);
            return new ResponseEntity<>(add, HttpStatus.OK);
        }else{
            throw new RuntimeException("You do not have required access");
        }
    }


    @GetMapping("/{addressId}")
    public ResponseEntity<AddressDto> getAddressById(@PathVariable UUID addressId) {
        return new ResponseEntity<>(addressService.getAddressById(addressId), HttpStatus.OK);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable UUID addressId) {
        addressService.deleteAddress(addressId);
        return new ResponseEntity<>("Address deleted successfully", HttpStatus.NO_CONTENT);
    }

}

