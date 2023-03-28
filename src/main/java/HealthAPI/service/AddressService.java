package HealthAPI.service;

import HealthAPI.converter.AddressConverter;
import HealthAPI.dto.AddressDto;
import HealthAPI.model.Address;
import HealthAPI.model.Client;
import HealthAPI.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressConverter addressConverter;

    @Autowired
    public AddressService(AddressRepository addressRepository, AddressConverter addressConverter) {
        this.addressRepository = addressRepository;
        this.addressConverter = addressConverter;
    }

    public Address saveAddress(AddressDto addressDto) {
        Address address = addressConverter.fromAddressDtoToAddress(addressDto);
        addressRepository.save(address);
        return address;
    }
}
