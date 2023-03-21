package HealthAPI.converter;

import HealthAPI.dto.ClientCreateDto;
import HealthAPI.model.Client;
import org.mapstruct.Mapper;

@Mapper
public interface ClientConverter {

    Client fromClientDtoToClient(ClientCreateDto clientCreateDto);

    ClientCreateDto fromClientToClientDto(Client client);

}