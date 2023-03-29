package HealthAPI.converter;

import HealthAPI.dto.client.AddressDto;
import HealthAPI.model.Address;
import org.mapstruct.Mapper;

@Mapper
public interface AddressConverter {

    Address fromAddressDtoToAddress(AddressDto addressDto);

}