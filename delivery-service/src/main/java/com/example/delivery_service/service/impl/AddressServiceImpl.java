package com.example.delivery_service.service.impl;

import com.example.delivery_service.dto.AddressDto;
import com.example.delivery_service.model.Address;
import com.example.delivery_service.repository.AddressRepository;
import com.example.delivery_service.service.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository,
                              ModelMapper modelMapper){

        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;

    }


    @Override
    public AddressDto updateAddress(UUID addressId, AddressDto addressDto) {
        /**
         First finding the saved address from the database
         if the address do not reside then throw corresponding error
         otherwise using modelmapper update the saved address and save it back to
         the database

         */
        Address savedAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Cannot find address with given Id"));

        modelMapper.map(addressDto, savedAddress);
        Address updatedAddress = addressRepository.save(savedAddress);
        return modelMapper.map(updatedAddress, AddressDto.class);
    }

    @Override
    public AddressDto getAddressById(UUID addressId) {
        /**
         Find the address using the given id and  if it does not reside then
         throwing corresponding error.
         */

        Address savedAddress = addressRepository.findById(addressId).orElseThrow(
                () -> new RuntimeException("Address not found")
        );
        return modelMapper.map(savedAddress, AddressDto.class);
    }

    @Override
    public void deleteAddress(UUID addressId) {
        /**
         Find the address using its id and then delete it otherwise throw
         corresponding error
         */

        if(!addressRepository.existsById(addressId)){
            throw new RuntimeException("Address not found");
        }
        addressRepository.deleteById(addressId);
    }
}
