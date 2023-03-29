package HealthAPI.converter;

import HealthAPI.dto.auth.RegisterRequest;
import HealthAPI.dto.client.AddressDto;
import HealthAPI.dto.client.AddressDto.AddressDtoBuilder;
import HealthAPI.dto.client.ClientDto;
import HealthAPI.dto.client.ClientDto.ClientDtoBuilder;
import HealthAPI.model.Address;
import HealthAPI.model.Address.AddressBuilder;
import HealthAPI.model.Client;
import HealthAPI.model.Client.ClientBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-29T15:51:32+0100",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
@Component
public class ClientConverterImpl implements ClientConverter {

    @Override
    public Client fromClientDtoToClient(ClientDto clientDto) {
        if ( clientDto == null ) {
            return null;
        }

        ClientBuilder client = Client.builder();

        client.id( clientDto.getId() );
        client.fullName( clientDto.getFullName() );
        client.phoneNumber( clientDto.getPhoneNumber() );
        client.email( clientDto.getEmail() );
        client.birthDate( clientDto.getBirthDate() );
        client.gender( clientDto.getGender() );
        if ( clientDto.getNIF() != null ) {
            client.NIF( clientDto.getNIF().intValue() );
        }
        client.address( addressDtoToAddress( clientDto.getAddress() ) );

        return client.build();
    }

    @Override
    public ClientDto fromClientToClientDto(Client client) {
        if ( client == null ) {
            return null;
        }

        ClientDtoBuilder clientDto = ClientDto.builder();

        clientDto.id( client.getId() );
        clientDto.fullName( client.getFullName() );
        clientDto.phoneNumber( client.getPhoneNumber() );
        clientDto.email( client.getEmail() );
        clientDto.birthDate( client.getBirthDate() );
        clientDto.gender( client.getGender() );
        clientDto.NIF( (long) client.getNIF() );
        clientDto.address( addressToAddressDto( client.getAddress() ) );

        return clientDto.build();
    }

    @Override
    public Client fromAuthenticationRequestToClient(RegisterRequest request) {
        if ( request == null ) {
            return null;
        }

        ClientBuilder client = Client.builder();

        client.fullName( request.getFullName() );
        client.phoneNumber( request.getPhoneNumber() );
        client.email( request.getEmail() );
        client.password( request.getPassword() );
        client.birthDate( request.getBirthDate() );
        client.gender( request.getGender() );
        client.NIF( request.getNIF() );
        client.address( addressDtoToAddress( request.getAddress() ) );

        return client.build();
    }

    protected Address addressDtoToAddress(AddressDto addressDto) {
        if ( addressDto == null ) {
            return null;
        }

        AddressBuilder address = Address.builder();

        address.street( addressDto.getStreet() );
        address.city( addressDto.getCity() );
        address.zipCode( addressDto.getZipCode() );

        return address.build();
    }

    protected AddressDto addressToAddressDto(Address address) {
        if ( address == null ) {
            return null;
        }

        AddressDtoBuilder addressDto = AddressDto.builder();

        addressDto.street( address.getStreet() );
        addressDto.city( address.getCity() );
        addressDto.zipCode( address.getZipCode() );

        return addressDto.build();
    }
}
