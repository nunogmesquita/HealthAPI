package HealthAPI.service;

import HealthAPI.converter.AddressConverter;
import HealthAPI.converter.ClientConverter;
import HealthAPI.dto.Client.ClientDto;
import HealthAPI.dto.RegisterRequest;
import HealthAPI.model.Client;
import HealthAPI.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientConverter clientConverter;
    private final AddressConverter addressConverter;

    @Autowired
    public ClientService(ClientRepository clientRepository, ClientConverter clientConverter, AddressConverter addressConverter) {
        this.clientRepository = clientRepository;
        this.clientConverter = clientConverter;
        this.addressConverter = addressConverter;
    }

    public ClientDto getClientByToken(String jwt) {
        Client client = clientRepository.findByTokens(jwt);
        return clientConverter.fromClientToClientDto(client);
    }

    public void deleteClient(Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow();
        client.markAsDeleted();
        clientRepository.save(client);
    }

    public List<ClientDto> getAllClients() {
        List<Client> clients = clientRepository.findByDeleted(false);
        return clients.parallelStream()
                .map(clientConverter::fromClientToClientDto)
                .toList();
    }

    public ClientDto updateClient(Long id, RegisterRequest registerRequest) {
        Client client = clientRepository.getReferenceById(id);
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

    @Cacheable(value = "clientCache")
    public ClientDto getClientById(Long id) {
        Client client = clientRepository.findByIdAndDeleted(id, false)
                .orElseThrow();
        return clientConverter.fromClientToClientDto(client);
    }

}