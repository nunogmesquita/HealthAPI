package HealthAPI.controller;

import HealthAPI.dto.auth.AuthenticationRequest;
import HealthAPI.dto.auth.AuthenticationResponse;
import HealthAPI.dto.auth.RegisterRequest;
import HealthAPI.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse authUser(@RequestBody AuthenticationRequest request) {
        return authenticationService.authenticateUser(request);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse register (@Valid @RequestBody RegisterRequest request) {
        return authenticationService.register(request);
    }

    @PostMapping("/client")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse authClient(@RequestBody AuthenticationRequest request) {
        return authenticationService.authenticateClient(request);
    }

}