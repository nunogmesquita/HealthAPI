package HealthAPI.controller;

import HealthAPI.dto.AuthenticationRequest;
import HealthAPI.dto.AuthenticationResponse;
import HealthAPI.dto.ClientCreateDto;
import HealthAPI.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody ClientCreateDto request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/user")
    public ResponseEntity<AuthenticationResponse> authUser(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticateUser(request));
    }

    @PostMapping("/client")
    public ResponseEntity<AuthenticationResponse> authClient(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticateClient(request));
    }

}