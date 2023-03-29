package HealthAPI.service;

import HealthAPI.converter.AddressConverter;
import HealthAPI.dto.client.AddressDto;
import HealthAPI.model.Address;
import HealthAPI.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressConverter addressConverter;

    public Address saveAddress(AddressDto addressDto) {
        Address address = addressConverter.fromAddressDtoToAddress(addressDto);
        addressRepository.save(address);
        return address;
    }

}
