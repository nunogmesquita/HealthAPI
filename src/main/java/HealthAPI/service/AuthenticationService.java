package HealthAPI.service;

import HealthAPI.dto.AuthenticationRequest;
import HealthAPI.dto.AuthenticationResponse;
import HealthAPI.dto.Client.ClientCreateDto;
import HealthAPI.model.Client;
import HealthAPI.model.User;

public interface AuthenticationService {

    AuthenticationResponse register(ClientCreateDto request);

    AuthenticationResponse authenticateUser(AuthenticationRequest request);

    AuthenticationResponse authenticateClient(AuthenticationRequest request);

    void saveClientToken(Client client, String jwtToken);

    void saveUserToken(User user, String jwtToken);

    void revokeAllUserTokens(User user);
}