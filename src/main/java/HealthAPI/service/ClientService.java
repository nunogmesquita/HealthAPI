package HealthAPI.service;

import HealthAPI.converter.AddressConverter;
import HealthAPI.converter.ClientConverter;
import HealthAPI.dto.client.ClientDto;
import HealthAPI.dto.auth.RegisterRequest;
import HealthAPI.dto.client.ClientUpdateDto;
import HealthAPI.exception.ClientNotFound;
import HealthAPI.messages.Responses;
import HealthAPI.model.Client;
import HealthAPI.model.NullUtils;
import HealthAPI.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientConverter clientConverter;
    private final AddressConverter addressConverter;

    public void deleteClient(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientNotFound(Responses.CLIENT_NOT_FOUND.formatted(clientId)));
        client.markAsDeleted();
        clientRepository.save(client);
    }

    public ClientDto updateClient(Long clientId, ClientUpdateDto clientUpdateDto) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientNotFound(Responses.CLIENT_NOT_FOUND.formatted(clientId)));
        NullUtils.updateIfPresent(client::setFullName, clientUpdateDto.getFullName());
        NullUtils.updateIfPresent(client::setPhoneNumber, clientUpdateDto.getPhoneNumber());
        NullUtils.updateIfPresent(client::setEmail, clientUpdateDto.getEmail());
        NullUtils.updateIfPresent(client::setPassword, clientUpdateDto.getPassword());
        NullUtils.updateIfPresent(client::setBirthDate, clientUpdateDto.getBirthDate());
        NullUtils.updateIfPresent(client::setGender, clientUpdateDto.getGender());
        NullUtils.updateIfPresent(client::setNIF, clientUpdateDto.getNIF());
        NullUtils.updateIfPresent(client::setAddress, addressConverter.fromAddressDtoToAddress(clientUpdateDto.getAddress()));
        clientRepository.save(client);
        return clientConverter.fromClientToClientDto(client);
    }

    public ClientDto updateClient(String clientEmail, ClientUpdateDto clientUpdateDto) {
        Client client = clientRepository.findByEmail(clientEmail)
                        .orElseThrow(() -> new ClientNotFound(Responses.CLIENT_NOT_FOUND.formatted(clientEmail)));
        NullUtils.updateIfPresent(client::setFullName, clientUpdateDto.getFullName());
        NullUtils.updateIfPresent(client::setPhoneNumber, clientUpdateDto.getPhoneNumber());
        NullUtils.updateIfPresent(client::setEmail, clientUpdateDto.getEmail());
        NullUtils.updateIfPresent(client::setPassword, clientUpdateDto.getPassword());
        NullUtils.updateIfPresent(client::setBirthDate, clientUpdateDto.getBirthDate());
        NullUtils.updateIfPresent(client::setGender, clientUpdateDto.getGender());
        NullUtils.updateIfPresent(client::setNIF, clientUpdateDto.getNIF());
        NullUtils.updateIfPresent(client::setAddress, addressConverter.fromAddressDtoToAddress(clientUpdateDto.getAddress()));
        clientRepository.save(client);
        return clientConverter.fromClientToClientDto(client);
    }

    public ClientDto getClientByEmail(String clientEmail) {
        Client client = clientRepository.findByEmail(clientEmail)
                .orElseThrow(() -> new ClientNotFound(Responses.CLIENT_NOT_FOUND.formatted(clientEmail)));
        return clientConverter.fromClientToClientDto(client);
    }

    public ClientDto getClientById(Long clientId) {
        Client client = clientRepository.findByIdAndDeletedFalse(clientId)
                .orElseThrow(() -> new ClientNotFound(Responses.CLIENT_NOT_FOUND.formatted(clientId)));
        return clientConverter.fromClientToClientDto(client);
    }

    public List<ClientDto> getAllClients() {
        List<Client> clients = clientRepository.findByDeletedFalse();
        return clients.parallelStream()
                .map(clientConverter::fromClientToClientDto)
                .toList();
    }

}