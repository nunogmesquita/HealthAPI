package HealthAPI.converter;

import HealthAPI.dto.AddressDto;
import HealthAPI.model.Address;
import HealthAPI.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AddressConverter {

    Address fromAddressDtoToAddress(AddressDto addressDto);

    AddressDto fromAddressToAddressDto(Address address);
}
