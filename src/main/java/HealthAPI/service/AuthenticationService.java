package HealthAPI.service;

import HealthAPI.converter.ClientConverter;
import HealthAPI.dto.AuthenticationRequest;
import HealthAPI.dto.AuthenticationResponse;
import HealthAPI.dto.RegisterRequest;
import HealthAPI.exception.NotOldEnough;
import HealthAPI.messages.Responses;
import HealthAPI.model.*;
import HealthAPI.repository.ClientRepository;
import HealthAPI.repository.TokenRepository;
import HealthAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final TokenRepository tokenRepository;
    private final ClientConverter clientConverter;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AddressService addressService;

    @Autowired
    public AuthenticationService(UserRepository userRepository, ClientRepository clientRepository,
                                 TokenRepository tokenRepository, ClientConverter clientConverter,
                                 JwtService jwtService, AuthenticationManager authenticationManager, AddressService addressService) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.tokenRepository = tokenRepository;
        this.clientConverter = clientConverter;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.addressService = addressService;
    }

    public AuthenticationResponse register(RegisterRequest request) {
        if (Period.between(request.getBirthDate(), LocalDate.now()).getYears() < 18) {
            throw new NotOldEnough();
        }
        Address address = addressService.saveAddress(request.getAddress());
        Client client = clientConverter.fromAuthenticationRequestToClient(request);
        client.setAddress(address);
        clientRepository.save(client);
        String jwtToken = jwtService.generateToken(client, client.getId());
        saveClientToken(client, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticateClient(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        Client client = clientRepository.findByEmail(request.getEmail())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(client, client.getId());
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

    public AuthenticationResponse authenticateUser(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(user, user.getId());
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
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

}