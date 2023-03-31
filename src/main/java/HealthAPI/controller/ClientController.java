package HealthAPI.controller;

import HealthAPI.dto.client.ClientDto;
import HealthAPI.dto.client.ClientUpdateDto;
import HealthAPI.exception.UnauthorizedAction;
import HealthAPI.messages.Responses;
import HealthAPI.service.ClientServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientServiceImpl clientService;

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{clientId}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteAccount(@PathVariable Long clientId) {
        clientService.deleteClient(clientId);
        return Responses.DELETED_CLIENT.formatted(clientId);
    }

    @Secured({"ROLE_ADMIN"})
    @PatchMapping("/{clientId}")
    @ResponseStatus(HttpStatus.OK)
    public ClientDto updateAccount(@PathVariable Long clientId,
                                   @Valid @RequestBody ClientUpdateDto clientUpdateDto) {
        return clientService.updateClient(clientId, clientUpdateDto);
    }

    @Secured("ROLE_VIEWER")
    @GetMapping("/myaccount")
    @ResponseStatus(HttpStatus.OK)
    @Cacheable(value = "viewMyAccount")
    public ClientDto viewMyAccount(@NonNull HttpServletRequest header) {
        String user = header.getUserPrincipal().getName();
        if ((header.getAttribute("ROLE")).toString().compareTo("VIEWER") != 0) {
            throw new UnauthorizedAction();
        }
        return clientService.getClientByEmail(user);
    }

    @Secured("ROLE_VIEWER")
    @PatchMapping("/myaccount")
    @ResponseStatus(HttpStatus.OK)
    public ClientDto updateMyAccount(@NonNull HttpServletRequest header,
                                     @Valid @RequestBody ClientUpdateDto clientUpdateDto) {
        String clientEmail = header.getUserPrincipal().getName();
        if ((header.getAttribute("ROLE")).toString().compareTo("VIEWER") != 0) {
            throw new UnauthorizedAction();
        }
        return clientService.updateClient(clientEmail, clientUpdateDto);
    }

    @Secured({"ROLE_ADMIN", "ROLE_HEALTHCAREPROVIDER"})
    @GetMapping("/{clientId}")
    @ResponseStatus(HttpStatus.OK)
    @Cacheable(value = "getClient", key = "#clientId")
    public ClientDto getClient(@PathVariable Long clientId) {
        return clientService.getClientById(clientId);
    }

    @Secured({"ROLE_ADMIN", "ROLE_HEALTHCAREPROVIDER"})
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<ClientDto> getAllClients() {
        return clientService.getAllClients();
    }

}