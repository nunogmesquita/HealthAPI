package HealthAPI.service;

import HealthAPI.converter.AddressConverter;
import HealthAPI.converter.ClientConverter;
import HealthAPI.dto.client.ClientDto;
import HealthAPI.dto.auth.RegisterRequest;
import HealthAPI.exception.ClientNotFound;
import HealthAPI.messages.Responses;
import HealthAPI.model.Client;
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

    public ClientDto updateClient(Long clientId, RegisterRequest registerRequest) {
        Client client = clientRepository.getReferenceById(clientId);
        client.setFullName(registerRequest.getFullName());
        client.setPhoneNumber(registerRequest.getPhoneNumber());
        client.setEmail(registerRequest.getEmail());
        client.setPassword(registerRequest.getPassword());
        client.setBirthDate(registerRequest.getBirthDate());
        client.setGender(registerRequest.getGender());
        client.setNIF(registerRequest.getNIF());
        client.setAddress(addressConverter.fromAddressDtoToAddress(registerRequest.getAddress()));
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