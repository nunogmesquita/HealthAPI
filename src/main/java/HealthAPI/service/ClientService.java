package HealthAPI.service;

import HealthAPI.dto.Client.ClientCreateDto;
import HealthAPI.dto.Client.ClientDto;
import HealthAPI.model.Client;

import java.util.List;

public interface ClientService {

    ClientDto getClientByToken(String jwt);

    void deleteClient(Long id);

    List<ClientDto> getAllClients();

    ClientDto updateClient(Long id, ClientCreateDto clientCreateDto);

    Client getClientById (Long id);
}
