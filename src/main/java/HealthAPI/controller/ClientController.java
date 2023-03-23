package HealthAPI.controller;

import HealthAPI.converter.ClientConverter;
import HealthAPI.converter.UserConverter;
import HealthAPI.dto.*;
import HealthAPI.model.Client;
import HealthAPI.model.User;
import HealthAPI.service.AuthenticationService;
import HealthAPI.service.ClientService;
import HealthAPI.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class ClientController {
    private AuthenticationService authenticationService;
    private UserConverter userConverter;
    private ClientService clientService;
    private UserService userService;
    private ClientConverter clientConverter;

    @Autowired
    public ClientController(AuthenticationService authenticationService, UserConverter userConverter) {
        this.authenticationService = authenticationService;
        this.userConverter = userConverter;
    }

    @GetMapping("/myaccount")
    @Secured("ROLE_CLIENT")
    public ResponseEntity<ClientDto> viewMyAccount(@NonNull HttpServletRequest header) {
        String jwt = header.getHeader("Authorization").substring(7);
        Client client = clientService.getClientByToken(jwt);
        ClientDto clientDto = clientConverter.fromClientToClientDto(client);
        return new ResponseEntity<>(clientDto, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAvailableDoctors() {
        List<User> hcproviders = userService.getHealthCareProviders();
        return new ResponseEntity<>(hcproviders, HttpStatus.OK);
    }

    @PatchMapping("/myAccount")
    @Secured({"ROLE_ADMIN", "ROLE_COLLABORATOR", "ROLE_HEALTHCAREPROVIDERS"})
    public ResponseEntity<ClientDto> updateMyAccount(@NonNull HttpServletRequest request, @Valid @RequestBody ClientDto clientDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                System.out.println(error.getObjectName() + " - " + error.getDefaultMessage());
            }
        }
        String jwt = request.getHeader("Authorization").substring(7);
        Client client = clientService.getClientByToken(jwt);
        ClientDto clientUpdated = clientService.updateMyAccount(client, clientDto);
        return new ResponseEntity<>(clientUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        clientService.deleteClient(id);
        return new ResponseEntity<>("Client has been deleted, mdf!", HttpStatus.OK);
    }

}