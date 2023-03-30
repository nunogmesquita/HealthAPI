package HealthAPI.service;

import HealthAPI.dto.auth.AuthenticationRequest;
import HealthAPI.dto.auth.AuthenticationResponse;
import HealthAPI.dto.auth.RegisterRequest;
import HealthAPI.model.*;

public interface AuthenticationService {

    AuthenticationResponse authenticateUser(AuthenticationRequest request);

    void revokeAllUserTokens(User user);

    void saveUserToken(User user, String jwtToken);

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticateClient(AuthenticationRequest request);

    void revokeAllClientTokens(Client client);

    void saveClientToken(Client client, String jwtToken);

}