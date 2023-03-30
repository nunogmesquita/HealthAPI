package HealthAPI.controller;

import HealthAPI.dto.client.ClientDto;
import HealthAPI.dto.client.ClientUpdateDto;
import HealthAPI.messages.Responses;
import HealthAPI.service.ClientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @DeleteMapping("/{clientId}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteAccount(@PathVariable Long clientId) {
        clientService.deleteClient(clientId);
        return Responses.DELETED_CLIENT.formatted(clientId);
    }

    @PatchMapping("/{clientId}")
    @ResponseStatus(HttpStatus.OK)
    public ClientDto updateAccount(@PathVariable Long clientId,
                                   @Valid @RequestBody ClientUpdateDto clientUpdateDto) {
        return clientService.updateClient(clientId, clientUpdateDto);
    }

    @GetMapping("/myaccount")
    @ResponseStatus(HttpStatus.OK)
    @Cacheable(value = "viewMyAccount")
    public ClientDto viewMyAccount(@NonNull HttpServletRequest header) {
        String user = header.getUserPrincipal().getName();
        return clientService.getClientByEmail(user);
    }

    @PatchMapping("/myaccount")
    @ResponseStatus(HttpStatus.OK)
    public ClientDto updateMyAccount(@NonNull HttpServletRequest header,
                                     @Valid @RequestBody ClientUpdateDto clientUpdateDto) {
        String clientEmail = header.getUserPrincipal().getName();
        return clientService.updateClient(clientEmail, clientUpdateDto);
    }

    @GetMapping("/{clientId}")
    @ResponseStatus(HttpStatus.OK)
    @Cacheable(value = "getClient", key = "#clientId")
    public ClientDto getClient(@PathVariable Long clientId) {
        return clientService.getClientById(clientId);
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<ClientDto> getAllClients() {
        return clientService.getAllClients();
    }

}