package HealthAPI.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import HealthAPI.converter.AddressConverter;
import HealthAPI.converter.ClientConverter;
import HealthAPI.dto.client.AddressDto;
import HealthAPI.dto.client.ClientDto;
import HealthAPI.dto.client.ClientUpdateDto;
import HealthAPI.model.Address;
import HealthAPI.model.Client;
import HealthAPI.repository.ClientRepository;

import java.util.ArrayList;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ClientServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ClientServiceTest {
    @MockBean
    private AddressConverter addressConverter;

    @MockBean
    private ClientConverter clientConverter;

    @MockBean
    private ClientRepository clientRepository;

    @Autowired
    private ClientServiceImpl clientService;

    @Test
    void testDeleteClient() {
        when(clientRepository.save((Client) any())).thenReturn(new Client());
        when(clientRepository.findById((Long) any())).thenReturn(Optional.of(new Client()));
        clientService.deleteClient(1L);
        verify(clientRepository).save((Client) any());
        verify(clientRepository).findById((Long) any());
    }
    @Test
    void testUpdateClient() {
        when(clientRepository.save((Client) any())).thenReturn(new Client());
        when(clientRepository.findById((Long) any())).thenReturn(Optional.of(new Client()));
        ClientDto clientDto = new ClientDto();
        when(clientConverter.fromClientToClientDto((Client) any())).thenReturn(clientDto);
        when(addressConverter.fromAddressDtoToAddress((AddressDto) any())).thenReturn(new Address());
        assertSame(clientDto, clientService.updateClient(1L, new ClientUpdateDto()));
        verify(clientRepository).save((Client) any());
        verify(clientRepository).findById((Long) any());
        verify(clientConverter).fromClientToClientDto((Client) any());
        verify(addressConverter).fromAddressDtoToAddress((AddressDto) any());
    }
    @Test
    void testGetClientByEmail() {
        when(clientRepository.findByEmail((String) any())).thenReturn(Optional.of(new Client()));
        ClientDto clientDto = new ClientDto();
        when(clientConverter.fromClientToClientDto((Client) any())).thenReturn(clientDto);
        assertSame(clientDto, clientService.getClientByEmail("jane.doe@example.org"));
        verify(clientRepository).findByEmail((String) any());
        verify(clientConverter).fromClientToClientDto((Client) any());
    }
    @Test
    void testGetClientById() {
        when(clientRepository.findByIdAndDeletedFalse((Long) any())).thenReturn(Optional.of(new Client()));
        ClientDto clientDto = new ClientDto();
        when(clientConverter.fromClientToClientDto((Client) any())).thenReturn(clientDto);
        assertSame(clientDto, clientService.getClientById(1L));
        verify(clientRepository).findByIdAndDeletedFalse((Long) any());
        verify(clientConverter).fromClientToClientDto((Client) any());
    }

    @Test
    void testGetAllClients() {
        when(clientRepository.findByDeletedFalse()).thenReturn(new ArrayList<>());
        assertTrue(clientService.getAllClients().isEmpty());
        verify(clientRepository).findByDeletedFalse();
    }
}

