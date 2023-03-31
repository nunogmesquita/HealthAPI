package HealthAPI.service;

import HealthAPI.converter.AddressConverter;
import HealthAPI.converter.ClientConverter;
import HealthAPI.dto.client.ClientDto;
import HealthAPI.dto.client.ClientUpdateDto;
import HealthAPI.exception.ClientNotFound;
import HealthAPI.exception.InvalidNIF;
import HealthAPI.exception.InvalidPhoneNumber;
import HealthAPI.model.Client;
import HealthAPI.repository.ClientRepository;
import HealthAPI.util.NullUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static HealthAPI.util.NIFVerify.verifyNif;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientConverter clientConverter;
    private final AddressConverter addressConverter;

    public void deleteClient(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(ClientNotFound::new);
        client.markAsDeleted();
        clientRepository.save(client);
    }

    public ClientDto updateClient(Long clientId, ClientUpdateDto clientUpdateDto) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(ClientNotFound::new);
        if (clientUpdateDto.getPhoneNumber() != 0 || Integer.toString(clientUpdateDto.getPhoneNumber()).matches("^[9][1236]\\d{7}$")) {
            throw new InvalidPhoneNumber();
        }
        client.setPhoneNumber(clientUpdateDto.getPhoneNumber());
        if (clientUpdateDto.getNIF() != 0 || Integer.toString(clientUpdateDto.getNIF()).matches("^\\d{9}$") || !verifyNif(clientUpdateDto.getNIF())) {
            throw new InvalidNIF();
        }
        client.setNIF(clientUpdateDto.getNIF());
        NullUtils.updateIfPresent(client::setFullName, clientUpdateDto.getFullName());
        NullUtils.updateIfPresent(client::setPhoneNumber, clientUpdateDto.getPhoneNumber());
        NullUtils.updateIfPresent(client::setEmail, clientUpdateDto.getEmail());
        NullUtils.updateIfPresent(client::setPassword, clientUpdateDto.getPassword());
        NullUtils.updateIfPresent(client::setBirthDate, clientUpdateDto.getBirthDate());
        NullUtils.updateIfPresent(client::setGender, clientUpdateDto.getGender());
        NullUtils.updateIfPresent(client::setNIF, clientUpdateDto.getNIF());
        NullUtils.updateIfPresent(client::setAddress, addressConverter.fromAddressDtoToAddress(clientUpdateDto.getAddressDto()));
        clientRepository.save(client);
        return clientConverter.fromClientToClientDto(client);
    }

    public ClientDto updateClient(String clientEmail, ClientUpdateDto clientUpdateDto) {
        Client client = clientRepository.findByEmail(clientEmail)
                .orElseThrow(ClientNotFound::new);
        if (clientUpdateDto.getPhoneNumber() != 0) {
            if (!Integer.toString(clientUpdateDto.getPhoneNumber()).matches("^[9][1236]\\d{7}$")) {
                throw new InvalidPhoneNumber();
            } else client.setPhoneNumber(clientUpdateDto.getPhoneNumber());
        }
        if (clientUpdateDto.getNIF() != 0) {
            if (!Integer.toString(clientUpdateDto.getNIF()).matches("^\\d{9}$") || !verifyNif(clientUpdateDto.getNIF())) {
                throw new InvalidNIF();
            } else client.setNIF(clientUpdateDto.getNIF());
        }
        NullUtils.updateIfPresent(client::setFullName, clientUpdateDto.getFullName());
        NullUtils.updateIfPresent(client::setEmail, clientUpdateDto.getEmail());
        NullUtils.updateIfPresent(client::setPassword, clientUpdateDto.getPassword());
        NullUtils.updateIfPresent(client::setBirthDate, clientUpdateDto.getBirthDate());
        NullUtils.updateIfPresent(client::setGender, clientUpdateDto.getGender());
        NullUtils.updateIfPresent(client::setAddress, addressConverter.fromAddressDtoToAddress(clientUpdateDto.getAddressDto()));
        clientRepository.save(client);
        return clientConverter.fromClientToClientDto(client);
    }

    public ClientDto getClientByEmail(String clientEmail) {
        Client client = clientRepository.findByEmail(clientEmail)
                .orElseThrow(ClientNotFound::new);
        return clientConverter.fromClientToClientDto(client);
    }

    public ClientDto getClientById(Long clientId) {
        Client client = clientRepository.findByIdAndDeleted(clientId, false)
                .orElseThrow(ClientNotFound::new);
        return clientConverter.fromClientToClientDto(client);
    }

    public List<ClientDto> getAllClients() {
        List<Client> clients = clientRepository.findByDeleted(false);
        return clients.parallelStream()
                .map(clientConverter::fromClientToClientDto)
                .toList();
    }

}