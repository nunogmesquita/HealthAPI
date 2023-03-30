package HealthAPI.service;

import HealthAPI.dto.client.ClientDto;
import HealthAPI.dto.client.ClientUpdateDto;

import java.util.List;

public interface ClientService {

    void deleteClient(Long clientId);

    ClientDto updateClient(Long clientId, ClientUpdateDto clientUpdateDto);

    ClientDto updateClient(String clientEmail, ClientUpdateDto clientUpdateDto);

    ClientDto getClientByEmail(String clientEmail);

    ClientDto getClientById(Long clientId);

    List<ClientDto> getAllClients();

}