package HealthAPI.converter;

import HealthAPI.dto.client.AddressDto;
import HealthAPI.model.Address;
import HealthAPI.model.Address.AddressBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-30T22:33:33+0100",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
@Component
public class AddressConverterImpl implements AddressConverter {

    @Override
    public Address fromAddressDtoToAddress(AddressDto addressDto) {
        if ( addressDto == null ) {
            return null;
        }

        AddressBuilder address = Address.builder();

        address.street( addressDto.getStreet() );
        address.city( addressDto.getCity() );
        address.zipCode( addressDto.getZipCode() );

        return address.build();
    }
}
