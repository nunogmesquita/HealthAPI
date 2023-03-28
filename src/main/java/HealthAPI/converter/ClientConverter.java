package HealthAPI.converter;

import HealthAPI.dto.Client.ClientDto;
import HealthAPI.dto.RegisterRequest;
import HealthAPI.model.Client;
import lombok.Builder;
import org.mapstruct.Mapper;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper
public interface ClientConverter {

    Client fromClientDtoToClient(ClientDto clientDto);

    ClientDto fromClientToClientDto(Client client);

    Client fromAuthenticationRequestToClient(RegisterRequest request);


}