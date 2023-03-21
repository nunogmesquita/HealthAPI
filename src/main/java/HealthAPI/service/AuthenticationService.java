package HealthAPI.service;


import HealthAPI.dto.AuthenticationRequest;
import HealthAPI.dto.AuthenticationResponse;
import HealthAPI.dto.ClientCreateDto;
import HealthAPI.dto.UserCreateDto;
import HealthAPI.model.Client;
import HealthAPI.model.User;

public interface AuthenticationService {

    AuthenticationResponse register(ClientCreateDto request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void saveClientToken(Client client, String jwtToken);

    void saveUserToken(User user, String jwtToken);

    void revokeAllUserTokens(User user);

}