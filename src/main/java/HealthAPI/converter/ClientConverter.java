package HealthAPI.converter;

import HealthAPI.dto.Client.ClientCreateDto;
import HealthAPI.dto.Client.ClientDto;
import HealthAPI.model.Client;
import org.mapstruct.Mapper;

@Mapper
public interface ClientConverter {

    Client fromClientCreateDtoToClient(ClientCreateDto clientCreateDto);

    ClientCreateDto fromClientToClientCreateDto(Client client);

    Client fromClientDtoToClient(ClientDto clientDto);

    ClientDto fromClientToClientDto(Client client);

}