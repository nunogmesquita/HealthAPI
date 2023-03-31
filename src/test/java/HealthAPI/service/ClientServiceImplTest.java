package HealthAPI.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import HealthAPI.converter.AddressConverter;
import HealthAPI.converter.ClientConverter;
import HealthAPI.dto.client.AddressDto;
import HealthAPI.dto.client.ClientDto;
import HealthAPI.dto.client.ClientUpdateDto;
import HealthAPI.exception.ClientNotFound;
import HealthAPI.exception.InvalidNIF;
import HealthAPI.exception.InvalidPhoneNumber;
import HealthAPI.model.Address;
import HealthAPI.model.Client;
import HealthAPI.model.Gender;
import HealthAPI.repository.ClientRepository;

import java.time.LocalDate;

import java.util.ArrayList;

import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ClientServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ClientServiceImplTest {
    @MockBean
    private AddressConverter addressConverter;

    @MockBean
    private ClientConverter clientConverter;

    @MockBean
    private ClientRepository clientRepository;

    @Autowired
    private ClientServiceImpl clientServiceImpl;

    @Test
    void testDeleteClient() {
        when(clientRepository.save((Client) any())).thenReturn(new Client());
        when(clientRepository.findById((Long) any())).thenReturn(Optional.of(new Client()));
        clientServiceImpl.deleteClient(1L);
        verify(clientRepository).save((Client) any());
        verify(clientRepository).findById((Long) any());
    }

    @Test
    void testDeleteClient2() {
        when(clientRepository.save((Client) any())).thenThrow(new InvalidPhoneNumber());
        when(clientRepository.findById((Long) any())).thenReturn(Optional.of(new Client()));
        assertThrows(InvalidPhoneNumber.class, () -> clientServiceImpl.deleteClient(1L));
        verify(clientRepository).save((Client) any());
        verify(clientRepository).findById((Long) any());
    }

    @Test
    void testDeleteClient3() {
        Client client = mock(Client.class);
        doNothing().when(client).markAsDeleted();
        Optional<Client> ofResult = Optional.of(client);
        when(clientRepository.save((Client) any())).thenReturn(new Client());
        when(clientRepository.findById((Long) any())).thenReturn(ofResult);
        clientServiceImpl.deleteClient(1L);
        verify(clientRepository).save((Client) any());
        verify(clientRepository).findById((Long) any());
        verify(client).markAsDeleted();
    }

    @Test
    void testDeleteClient5() {
        Client client = mock(Client.class);
        doThrow(new InvalidPhoneNumber()).when(client).markAsDeleted();
        Optional<Client> ofResult = Optional.of(client);
        when(clientRepository.save((Client) any())).thenReturn(new Client());
        when(clientRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(InvalidPhoneNumber.class, () -> clientServiceImpl.deleteClient(1L));
        verify(clientRepository).findById((Long) any());
        verify(client).markAsDeleted();
    }

    @Test
    void testUpdateClient() {
        when(clientRepository.save((Client) any())).thenReturn(new Client());
        when(clientRepository.findById((Long) any())).thenReturn(Optional.of(new Client()));
        ClientDto clientDto = new ClientDto();
        when(clientConverter.fromClientToClientDto((Client) any())).thenReturn(clientDto);
        when(addressConverter.fromAddressDtoToAddress((AddressDto) any())).thenReturn(new Address());
        assertSame(clientDto, clientServiceImpl.updateClient(1L, new ClientUpdateDto()));
        verify(clientRepository).save((Client) any());
        verify(clientRepository).findById((Long) any());
        verify(clientConverter).fromClientToClientDto((Client) any());
        verify(addressConverter).fromAddressDtoToAddress((AddressDto) any());
    }

    @Test
    void testUpdateClient2() {
        when(clientRepository.save((Client) any())).thenReturn(new Client());
        when(clientRepository.findById((Long) any())).thenReturn(Optional.of(new Client()));
        when(clientConverter.fromClientToClientDto((Client) any())).thenReturn(new ClientDto());
        when(addressConverter.fromAddressDtoToAddress((AddressDto) any())).thenThrow(new InvalidPhoneNumber());
        assertThrows(InvalidPhoneNumber.class, () -> clientServiceImpl.updateClient(1L, new ClientUpdateDto()));
        verify(clientRepository).findById((Long) any());
        verify(addressConverter).fromAddressDtoToAddress((AddressDto) any());
    }

    @Test
    void testUpdateClient7() {
        when(clientRepository.save((Client) any())).thenReturn(new Client());
        when(clientRepository.findByEmail((String) any())).thenReturn(Optional.of(new Client()));
        ClientDto clientDto = new ClientDto();
        when(clientConverter.fromClientToClientDto((Client) any())).thenReturn(clientDto);
        when(addressConverter.fromAddressDtoToAddress((AddressDto) any())).thenReturn(new Address());
        assertSame(clientDto, clientServiceImpl.updateClient("jane.doe@example.org", new ClientUpdateDto()));
        verify(clientRepository).save((Client) any());
        verify(clientRepository).findByEmail((String) any());
        verify(clientConverter).fromClientToClientDto((Client) any());
        verify(addressConverter).fromAddressDtoToAddress((AddressDto) any());
    }

    @Test
    void testUpdateClient8() {
        when(clientRepository.save((Client) any())).thenReturn(new Client());
        when(clientRepository.findByEmail((String) any())).thenReturn(Optional.of(new Client()));
        when(clientConverter.fromClientToClientDto((Client) any())).thenReturn(new ClientDto());
        when(addressConverter.fromAddressDtoToAddress((AddressDto) any())).thenThrow(new InvalidPhoneNumber());
        assertThrows(InvalidPhoneNumber.class,
                () -> clientServiceImpl.updateClient("jane.doe@example.org", new ClientUpdateDto()));
        verify(clientRepository).findByEmail((String) any());
        verify(addressConverter).fromAddressDtoToAddress((AddressDto) any());
    }

    @Test
    void testUpdateClient9() {
        Client client = mock(Client.class);
        doNothing().when(client).setAddress((Address) any());
        Optional<Client> ofResult = Optional.of(client);
        when(clientRepository.save((Client) any())).thenReturn(new Client());
        when(clientRepository.findByEmail((String) any())).thenReturn(ofResult);
        ClientDto clientDto = new ClientDto();
        when(clientConverter.fromClientToClientDto((Client) any())).thenReturn(clientDto);
        when(addressConverter.fromAddressDtoToAddress((AddressDto) any())).thenReturn(new Address());
        assertSame(clientDto, clientServiceImpl.updateClient("jane.doe@example.org", new ClientUpdateDto()));
        verify(clientRepository).save((Client) any());
        verify(clientRepository).findByEmail((String) any());
        verify(client).setAddress((Address) any());
        verify(clientConverter).fromClientToClientDto((Client) any());
        verify(addressConverter).fromAddressDtoToAddress((AddressDto) any());
    }

    @Test
    void testGetClientByEmail() {
        when(clientRepository.findByEmail((String) any())).thenReturn(Optional.of(new Client()));
        ClientDto clientDto = new ClientDto();
        when(clientConverter.fromClientToClientDto((Client) any())).thenReturn(clientDto);
        assertSame(clientDto, clientServiceImpl.getClientByEmail("jane.doe@example.org"));
        verify(clientRepository).findByEmail((String) any());
        verify(clientConverter).fromClientToClientDto((Client) any());
    }

    @Test
    void testGetClientByEmail2() {
        when(clientRepository.findByEmail((String) any())).thenReturn(Optional.of(new Client()));
        when(clientConverter.fromClientToClientDto((Client) any())).thenThrow(new InvalidPhoneNumber());
        assertThrows(InvalidPhoneNumber.class, () -> clientServiceImpl.getClientByEmail("jane.doe@example.org"));
        verify(clientRepository).findByEmail((String) any());
        verify(clientConverter).fromClientToClientDto((Client) any());
    }


    @Test
    void testGetClientById() {
        when(clientRepository.findByIdAndDeleted((Long) any(), anyBoolean())).thenReturn(Optional.of(new Client()));
        ClientDto clientDto = new ClientDto();
        when(clientConverter.fromClientToClientDto((Client) any())).thenReturn(clientDto);
        assertSame(clientDto, clientServiceImpl.getClientById(1L));
        verify(clientRepository).findByIdAndDeleted((Long) any(), anyBoolean());
        verify(clientConverter).fromClientToClientDto((Client) any());
    }

    @Test
    void testGetClientById2() {
        when(clientRepository.findByIdAndDeleted((Long) any(), anyBoolean())).thenReturn(Optional.of(new Client()));
        when(clientConverter.fromClientToClientDto((Client) any())).thenThrow(new InvalidPhoneNumber());
        assertThrows(InvalidPhoneNumber.class, () -> clientServiceImpl.getClientById(1L));
        verify(clientRepository).findByIdAndDeleted((Long) any(), anyBoolean());
        verify(clientConverter).fromClientToClientDto((Client) any());
    }

    @Test
    void testGetAllClients() {
        when(clientRepository.findByDeleted(anyBoolean())).thenReturn(new ArrayList<>());
        assertTrue(clientServiceImpl.getAllClients().isEmpty());
        verify(clientRepository).findByDeleted(anyBoolean());
    }
}

