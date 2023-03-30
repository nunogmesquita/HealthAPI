package HealthAPI.service;

import HealthAPI.config.JwtService;
import HealthAPI.converter.ClientConverter;
import HealthAPI.dto.auth.AuthenticationRequest;
import HealthAPI.dto.auth.AuthenticationResponse;
import HealthAPI.dto.auth.RegisterRequest;
import HealthAPI.exception.ClientNotFound;
import HealthAPI.exception.NotOldEnough;
import HealthAPI.exception.UserNotFound;
import HealthAPI.messages.Responses;
import HealthAPI.model.*;
import HealthAPI.repository.ClientRepository;
import HealthAPI.repository.TokenRepository;
import HealthAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final TokenRepository tokenRepository;
    private final ClientConverter clientConverter;
    private final JwtService jwtService;
    private final AddressService addressService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse authenticateUser(AuthenticationRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFound(Responses.USER_NOT_FOUND.formatted(request.getEmail())));
        String jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public AuthenticationResponse register(RegisterRequest request) {
        if (Period.between(request.getBirthDate(), LocalDate.now()).getYears() < 18) {
            throw new NotOldEnough();
        }
        Address address = addressService.saveAddress(request.getAddress());
        Client client = clientConverter.fromAuthenticationRequestToClient(request);
        client.setRole(Role.VIEWER);
        client.setPassword(passwordEncoder.encode(request.getPassword()));
        client.setAddress(address);
        clientRepository.save(client);
        String jwtToken = jwtService.generateToken(client);
        saveClientToken(client, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticateClient(AuthenticationRequest request) {
        Client client = clientRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ClientNotFound(Responses.CLIENT_NOT_FOUND.formatted(request.getEmail())));
        String jwtToken = jwtService.generateToken(client);
        revokeAllClientTokens(client);
        saveClientToken(client, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public void revokeAllClientTokens(Client client) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByClient(client.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void saveClientToken(Client client, String jwtToken) {
        Token clientToken = Token.builder()
                .client(client)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(clientToken);
    }

}