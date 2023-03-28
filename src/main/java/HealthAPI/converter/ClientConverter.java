package HealthAPI.converter;

import HealthAPI.dto.Client.ClientDto;
import HealthAPI.dto.RegisterRequest;
import HealthAPI.model.Client;
import lombok.Builder;
import org.mapstruct.Mapper;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper
public interface ClientConverter {

    PasswordEncoder passwordEncoder;


    Client fromClientDtoToClient(ClientDto clientDto);

    ClientDto fromClientToClientDto(Client client);

    Client fromAuthenticationRequestToClient(RegisterRequest request) {
        return Client.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .NIF(request.getNIF())
                .birthDate(request.getBirthDate())
                .gender(request.getGender())
                .password(passordEncoder.encode(request.getPassword()))
                .build();
    }

}