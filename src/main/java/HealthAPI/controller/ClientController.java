package HealthAPI.controller;

import HealthAPI.converter.UserConverter;
import HealthAPI.dto.AuthenticationRequest;
import HealthAPI.dto.AuthenticationResponse;
import HealthAPI.dto.ClientCreateDto;
import HealthAPI.service.AuthenticationService;
import HealthAPI.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class ClientController {
    private AuthenticationService authenticationService;
    private UserConverter userConverter;
    private ClientService clientService;

    @Autowired
    public ClientController(AuthenticationService authenticationService, UserConverter userConverter) {
        this.authenticationService = authenticationService;
        this.userConverter = userConverter;
    }

    @GetMapping("/myaccount")
    @Secured("ROLE_CLIENT")
    public ResponseEntity<ClientCreateDto> viewMyAccount(@RequestBody AuthenticationRequest request) {

        return ResponseEntity.ok(authenticationService.authenticateClient(request));
    }

    @PatchMapping("/update")
    @Secured({"ROLE_CLIENT, ROLE_COLLABORATOR, ROLE_ADMIN"})
    public ResponseEntity<AuthenticationResponse> updateAccount(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticateClient(request));
    }

    @DeleteMapping("/delete")
    @Secured({"ROLE_CLIENT, ROLE_COLLABORATOR, ROLE_ADMIN"})
    public ResponseEntity<AuthenticationResponse> delete(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticateClient(request));
    }

}