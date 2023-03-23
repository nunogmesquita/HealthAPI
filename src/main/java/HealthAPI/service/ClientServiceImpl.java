package HealthAPI.service;

import HealthAPI.converter.ClientConverter;
import HealthAPI.converter.UserConverter;
import HealthAPI.dto.ClientDto;
import HealthAPI.model.Client;
import HealthAPI.model.User;
import HealthAPI.repository.ClientRepository;
import HealthAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;
    private ClientConverter clientConverter;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, ClientConverter clientConverter) {
        this.clientRepository = clientRepository;
        this.clientConverter = clientConverter;
    }

    @Override
    public Client getClientByToken(String jwt) {
        return clientRepository.findByToken(jwt);
    }

    @Override
    public void deleteClient(Long clientId) {
        clientRepository.deleteById(clientId);
    }

    @Override
    public ClientDto updateMyAccount(Client client, ClientDto clientDto) {
        client.setName(clientDto.getName());
        client.setUserName(clientDto.getUserName());
        client.setEmail(clientDto.getEmail());
        clientRepository.save(client);
        return clientConverter.fromClientToClientDto(client);
    }
}