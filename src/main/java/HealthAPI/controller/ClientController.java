package HealthAPI.controller;

import HealthAPI.converter.UserConverter;
import HealthAPI.dto.AuthenticationRequest;
import HealthAPI.dto.AuthenticationResponse;
import HealthAPI.service.AuthenticationService;
import HealthAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class ClientController {
    private AuthenticationService service;
    private UserConverter userConverter;

    @Autowired
    public ClientController(AuthenticationService service, UserConverter userConverter) {
        this.service = service;
        this.userConverter = userConverter;
    }

    @GetMapping("/myaccount")
    @Secured("ROLE_CLIENT")
    public ResponseEntity<AuthenticationResponse> viewMyAccount(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PatchMapping("/update")
    @Secured({"ROLE_CLIENT, ROLE_COLLABORATOR, ROLE_ADMIN"})
    public ResponseEntity<AuthenticationResponse> updateAccount(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @DeleteMapping("/delete")
    @Secured({"ROLE_CLIENT, ROLE_COLLABORATOR, ROLE_ADMIN"})
    public ResponseEntity<AuthenticationResponse> delete(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

}