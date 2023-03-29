package HealthAPI.controller;

import HealthAPI.dto.auth.AuthenticationRequest;
import HealthAPI.dto.auth.AuthenticationResponse;
import HealthAPI.dto.auth.RegisterRequest;
import HealthAPI.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/user")
    public ResponseEntity<AuthenticationResponse> authUser(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticateUser(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/client")
    public ResponseEntity<AuthenticationResponse> authClient(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticateClient(request));
    }

}