package HealthAPI.converter;

import HealthAPI.dto.Client.ClientCreateDto;
import HealthAPI.dto.Client.ClientCreateDto.ClientCreateDtoBuilder;
import HealthAPI.dto.Client.ClientDto;
import HealthAPI.dto.Client.ClientDto.ClientDtoBuilder;
import HealthAPI.model.Client;
import HealthAPI.model.Client.ClientBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-27T09:58:53+0100",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
@Component
public class ClientConverterImpl implements ClientConverter {

    @Override
    public Client fromClientCreateDtoToClient(ClientCreateDto clientCreateDto) {
        if ( clientCreateDto == null ) {
            return null;
        }

        ClientBuilder client = Client.builder();

        client.fullName( clientCreateDto.getFullName() );
        client.phoneNumber( clientCreateDto.getPhoneNumber() );
        client.email( clientCreateDto.getEmail() );
        client.password( clientCreateDto.getPassword() );
        client.birthDate( clientCreateDto.getBirthDate() );
        client.gender( clientCreateDto.getGender() );
        client.NIF( clientCreateDto.getNIF() );
        client.address( clientCreateDto.getAddress() );

        return client.build();
    }

    @Override
    public ClientCreateDto fromClientToClientCreateDto(Client client) {
        if ( client == null ) {
            return null;
        }

        ClientCreateDtoBuilder clientCreateDto = ClientCreateDto.builder();

        clientCreateDto.fullName( client.getFullName() );
        clientCreateDto.phoneNumber( client.getPhoneNumber() );
        clientCreateDto.email( client.getEmail() );
        clientCreateDto.password( client.getPassword() );
        clientCreateDto.birthDate( client.getBirthDate() );
        clientCreateDto.gender( client.getGender() );
        clientCreateDto.NIF( client.getNIF() );
        clientCreateDto.address( client.getAddress() );

        return clientCreateDto.build();
    }

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
        client.NIF( clientDto.getNIF() );
        client.address( clientDto.getAddress() );

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
        clientDto.NIF( client.getNIF() );
        clientDto.address( client.getAddress() );

        return clientDto.build();
    }
}