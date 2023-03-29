package HealthAPI.converter;

import HealthAPI.dto.client.ClientDto;
import HealthAPI.dto.auth.RegisterRequest;
import HealthAPI.model.Client;
import org.mapstruct.Mapper;

@Mapper
public interface ClientConverter {

    Client fromClientDtoToClient(ClientDto clientDto);

    ClientDto fromClientToClientDto(Client client);

    Client fromAuthenticationRequestToClient(RegisterRequest request);

}