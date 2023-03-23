package HealthAPI.service;

import HealthAPI.dto.ClientDto;
import HealthAPI.model.Client;

public interface ClientService {
    Client getClientByToken(String jwt);

    void deleteClient(Long id);

    ClientDto updateMyAccount(Client client, ClientDto clientDto);
}
