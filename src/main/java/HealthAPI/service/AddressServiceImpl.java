package HealthAPI.service;

import HealthAPI.model.Address;
import HealthAPI.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    public Address saveAddress(Address address) {
        addressRepository.save(address);
        return address;
    }

}