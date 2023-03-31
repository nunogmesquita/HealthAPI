package HealthAPI.service;

import HealthAPI.config.JwtService;
import HealthAPI.converter.ClientConverter;
import HealthAPI.dto.auth.AuthenticationRequest;
import HealthAPI.dto.auth.AuthenticationResponse;
import HealthAPI.dto.auth.RegisterRequest;
import HealthAPI.exception.*;
import HealthAPI.model.*;
import HealthAPI.repository.ClientRepository;
import HealthAPI.repository.TokenRepository;
import HealthAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static HealthAPI.util.NIFVerify.verifyNif;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final TokenRepository tokenRepository;
    private final ClientConverter clientConverter;
    private final JwtService jwtService;
    private final AddressServiceImpl addressService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticateUser(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(UserNotFound::new);
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
        } else if (Period.between(request.getBirthDate(), LocalDate.now()).getYears() > 120) {
            throw new TooOld(Period.between(request.getBirthDate(), LocalDate.now()).getYears());
        } else if (!Integer.toString(request.getPhoneNumber()).matches("^[9][1236]\\d{7}$")) {
            throw new InvalidPhoneNumber();
        } else if (!Integer.toString(request.getNIF()).matches("^\\d{9}$") || !verifyNif(request.getNIF())) {
            throw new InvalidNIF();
        }
        Address address = addressService.saveAddress(Address.builder()
                .street(request.getStreet())
                .city(request.getCity())
                .zipCode(request.getZipCode())
                .build()
        );
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
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        Client client = clientRepository.findByEmail(request.getEmail())
                .orElseThrow(ClientNotFound::new);
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