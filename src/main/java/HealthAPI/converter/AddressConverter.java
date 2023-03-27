package HealthAPI.converter;

import HealthAPI.dto.AddressDto;
import HealthAPI.model.Address;
import org.mapstruct.Mapper;

@Mapper
public interface AddressConverter {

    Address fromAddressDtoToAddress(AddressDto addressDto);

    AddressDto fromAddressToAddressDto(Address address);
}
