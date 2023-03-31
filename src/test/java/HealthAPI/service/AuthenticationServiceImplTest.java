package HealthAPI.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import HealthAPI.config.JwtService;
import HealthAPI.converter.ClientConverter;
import HealthAPI.dto.auth.AuthenticationRequest;
import HealthAPI.dto.auth.RegisterRequest;
import HealthAPI.exception.NotOldEnough;
import HealthAPI.exception.TooOld;
import HealthAPI.model.Client;
import HealthAPI.model.Token;
import HealthAPI.model.User;
import HealthAPI.repository.ClientRepository;
import HealthAPI.repository.TokenRepository;
import HealthAPI.repository.UserRepository;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AuthenticationServiceImpl.class})
@ExtendWith(SpringExtension.class)
class AuthenticationServiceImplTest {
    @MockBean
    private AddressServiceImpl addressServiceImpl;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationServiceImpl authenticationServiceImpl;

    @MockBean
    private ClientConverter clientConverter;

    @MockBean
    private ClientRepository clientRepository;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private TokenRepository tokenRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testAuthenticateUser() throws AuthenticationException {
        when(userRepository.findByEmail((String) any())).thenReturn(Optional.of(new User()));
        when(tokenRepository.save((Token) any())).thenReturn(new Token());
        when(tokenRepository.findAllValidTokenByUser((Long) any())).thenReturn(new ArrayList<>());
        when(jwtService.generateToken((UserDetails) any())).thenReturn("ABC123");
        when(authenticationManager.authenticate((Authentication) any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        assertEquals("ABC123",
                authenticationServiceImpl.authenticateUser(new AuthenticationRequest("jane.doe@example.org", "iloveyou"))
                        .getToken());
        verify(userRepository).findByEmail((String) any());
        verify(tokenRepository).save((Token) any());
        verify(tokenRepository).findAllValidTokenByUser((Long) any());
        verify(jwtService).generateToken((UserDetails) any());
        verify(authenticationManager).authenticate((Authentication) any());
    }

    @Test
    void testAuthenticateUser2() throws AuthenticationException {
        when(userRepository.findByEmail((String) any())).thenReturn(Optional.of(new User()));
        when(tokenRepository.save((Token) any())).thenReturn(new Token());
        when(tokenRepository.findAllValidTokenByUser((Long) any())).thenReturn(new ArrayList<>());
        when(jwtService.generateToken((UserDetails) any())).thenReturn("ABC123");
        when(authenticationManager.authenticate((Authentication) any())).thenThrow(new NotOldEnough());
        assertThrows(NotOldEnough.class, () -> authenticationServiceImpl
                .authenticateUser(new AuthenticationRequest("jane.doe@example.org", "iloveyou")));
        verify(authenticationManager).authenticate((Authentication) any());
    }

    @Test
    void testAuthenticateUser3() throws AuthenticationException {
        User user = mock(User.class);
        when(user.getId()).thenThrow(new NotOldEnough());
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByEmail((String) any())).thenReturn(ofResult);
        when(tokenRepository.save((Token) any())).thenReturn(new Token());
        when(tokenRepository.findAllValidTokenByUser((Long) any())).thenReturn(new ArrayList<>());
        when(jwtService.generateToken((UserDetails) any())).thenReturn("ABC123");
        when(authenticationManager.authenticate((Authentication) any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        assertThrows(NotOldEnough.class, () -> authenticationServiceImpl
                .authenticateUser(new AuthenticationRequest("jane.doe@example.org", "iloveyou")));
        verify(userRepository).findByEmail((String) any());
        verify(user).getId();
        verify(jwtService).generateToken((UserDetails) any());
        verify(authenticationManager).authenticate((Authentication) any());
    }

    @Test
    void testRevokeAllUserTokens() {
        when(tokenRepository.findAllValidTokenByUser((Long) any())).thenReturn(new ArrayList<>());
        User user = new User();
        authenticationServiceImpl.revokeAllUserTokens(user);
        verify(tokenRepository).findAllValidTokenByUser((Long) any());
        assertFalse(user.isDeleted());
    }

    @Test
    void testRevokeAllUserTokens2() {
        ArrayList<Token> tokenList = new ArrayList<>();
        tokenList.add(new Token());
        when(tokenRepository.saveAll((Iterable<Token>) any())).thenReturn(new ArrayList<>());
        when(tokenRepository.findAllValidTokenByUser((Long) any())).thenReturn(tokenList);
        authenticationServiceImpl.revokeAllUserTokens(new User());
        verify(tokenRepository).findAllValidTokenByUser((Long) any());
        verify(tokenRepository).saveAll((Iterable<Token>) any());
    }

    @Test
    void testRevokeAllUserTokens3() {
        ArrayList<Token> tokenList = new ArrayList<>();
        tokenList.add(new Token());
        tokenList.add(new Token());
        when(tokenRepository.saveAll((Iterable<Token>) any())).thenReturn(new ArrayList<>());
        when(tokenRepository.findAllValidTokenByUser((Long) any())).thenReturn(tokenList);
        authenticationServiceImpl.revokeAllUserTokens(new User());
        verify(tokenRepository).findAllValidTokenByUser((Long) any());
        verify(tokenRepository).saveAll((Iterable<Token>) any());
    }

    @Test
    void testRevokeAllUserTokens5() {
        Token token = mock(Token.class);
        doNothing().when(token).setExpired(anyBoolean());
        doNothing().when(token).setRevoked(anyBoolean());

        ArrayList<Token> tokenList = new ArrayList<>();
        tokenList.add(token);
        when(tokenRepository.saveAll((Iterable<Token>) any())).thenReturn(new ArrayList<>());
        when(tokenRepository.findAllValidTokenByUser((Long) any())).thenReturn(tokenList);
        authenticationServiceImpl.revokeAllUserTokens(new User());
        verify(tokenRepository).findAllValidTokenByUser((Long) any());
        verify(tokenRepository).saveAll((Iterable<Token>) any());
        verify(token).setExpired(anyBoolean());
        verify(token).setRevoked(anyBoolean());
    }

    @Test
    void testSaveUserToken() {
        when(tokenRepository.save((Token) any())).thenReturn(new Token());
        authenticationServiceImpl.saveUserToken(new User(), "ABC123");
        verify(tokenRepository).save((Token) any());
    }

    @Test
    void testSaveUserToken2() {
        when(tokenRepository.save((Token) any())).thenThrow(new NotOldEnough());
        assertThrows(NotOldEnough.class, () -> authenticationServiceImpl.saveUserToken(new User(), "ABC123"));
        verify(tokenRepository).save((Token) any());
    }

    @Test
    void testRegister3() {
        RegisterRequest registerRequest = mock(RegisterRequest.class);
        when(registerRequest.getPhoneNumber()).thenReturn(10);
        when(registerRequest.getFullName()).thenReturn("U");
        when(registerRequest.getBirthDate()).thenReturn(LocalDate.ofYearDay(18, 18));
        assertThrows(TooOld.class, () -> authenticationServiceImpl.register(registerRequest));
        verify(registerRequest, atLeast(1)).getBirthDate();
    }

    @Test
    void testIsNameValid() {
        assertTrue(authenticationServiceImpl.isNameValid("Name"));
        assertTrue(authenticationServiceImpl.isNameValid("U"));
        assertFalse(authenticationServiceImpl.isNameValid("^[a-zA-Z]+$"));
        assertTrue(authenticationServiceImpl.isNameValid("UU"));
        assertTrue(authenticationServiceImpl.isNameValid("UName"));
        assertTrue(authenticationServiceImpl.isNameValid("NameU"));
        assertTrue(authenticationServiceImpl.isNameValid("NameName"));
        assertTrue(authenticationServiceImpl.isNameValid("UUU"));
        assertTrue(authenticationServiceImpl.isNameValid("UUName"));
        assertTrue(authenticationServiceImpl.isNameValid("UNameU"));
        assertTrue(authenticationServiceImpl.isNameValid("UNameName"));
        assertTrue(authenticationServiceImpl.isNameValid("NameUU"));
        assertTrue(authenticationServiceImpl.isNameValid("NameUName"));
        assertTrue(authenticationServiceImpl.isNameValid("NameNameU"));
        assertTrue(authenticationServiceImpl.isNameValid("NameNameName"));
    }

    @Test
    void testAuthenticateClient() throws AuthenticationException {
        when(clientRepository.findByEmail((String) any())).thenReturn(Optional.of(new Client()));
        when(tokenRepository.save((Token) any())).thenReturn(new Token());
        when(tokenRepository.findAllValidTokenByClient((Long) any())).thenReturn(new ArrayList<>());
        when(jwtService.generateToken((UserDetails) any())).thenReturn("ABC123");
        when(authenticationManager.authenticate((Authentication) any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        assertEquals("ABC123",
                authenticationServiceImpl.authenticateClient(new AuthenticationRequest("jane.doe@example.org", "iloveyou"))
                        .getToken());
        verify(clientRepository).findByEmail((String) any());
        verify(tokenRepository).save((Token) any());
        verify(tokenRepository).findAllValidTokenByClient((Long) any());
        verify(jwtService).generateToken((UserDetails) any());
        verify(authenticationManager).authenticate((Authentication) any());
    }

    @Test
    void testAuthenticateClient2() throws AuthenticationException {
        when(clientRepository.findByEmail((String) any())).thenReturn(Optional.of(new Client()));
        when(tokenRepository.save((Token) any())).thenReturn(new Token());
        when(tokenRepository.findAllValidTokenByClient((Long) any())).thenReturn(new ArrayList<>());
        when(jwtService.generateToken((UserDetails) any())).thenReturn("ABC123");
        when(authenticationManager.authenticate((Authentication) any())).thenThrow(new NotOldEnough());
        assertThrows(NotOldEnough.class, () -> authenticationServiceImpl
                .authenticateClient(new AuthenticationRequest("jane.doe@example.org", "iloveyou")));
        verify(authenticationManager).authenticate((Authentication) any());
    }

    @Test
    void testAuthenticateClient3() throws AuthenticationException {
        Client client = mock(Client.class);
        when(client.getId()).thenThrow(new NotOldEnough());
        Optional<Client> ofResult = Optional.of(client);
        when(clientRepository.findByEmail((String) any())).thenReturn(ofResult);
        when(tokenRepository.save((Token) any())).thenReturn(new Token());
        when(tokenRepository.findAllValidTokenByClient((Long) any())).thenReturn(new ArrayList<>());
        when(jwtService.generateToken((UserDetails) any())).thenReturn("ABC123");
        when(authenticationManager.authenticate((Authentication) any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        assertThrows(NotOldEnough.class, () -> authenticationServiceImpl
                .authenticateClient(new AuthenticationRequest("jane.doe@example.org", "iloveyou")));
        verify(clientRepository).findByEmail((String) any());
        verify(client).getId();
        verify(jwtService).generateToken((UserDetails) any());
        verify(authenticationManager).authenticate((Authentication) any());
    }

    @Test
    void testRevokeAllClientTokens() {
        when(tokenRepository.findAllValidTokenByClient((Long) any())).thenReturn(new ArrayList<>());
        Client client = new Client();
        authenticationServiceImpl.revokeAllClientTokens(client);
        verify(tokenRepository).findAllValidTokenByClient((Long) any());
        assertFalse(client.isDeleted());
        assertEquals(0, client.getPhoneNumber());
        assertEquals(0, client.getNIF());
    }

    @Test
    void testRevokeAllClientTokens2() {
        ArrayList<Token> tokenList = new ArrayList<>();
        tokenList.add(new Token());
        when(tokenRepository.saveAll((Iterable<Token>) any())).thenReturn(new ArrayList<>());
        when(tokenRepository.findAllValidTokenByClient((Long) any())).thenReturn(tokenList);
        authenticationServiceImpl.revokeAllClientTokens(new Client());
        verify(tokenRepository).findAllValidTokenByClient((Long) any());
        verify(tokenRepository).saveAll((Iterable<Token>) any());
    }

    @Test
    void testRevokeAllClientTokens3() {
        ArrayList<Token> tokenList = new ArrayList<>();
        tokenList.add(new Token());
        tokenList.add(new Token());
        when(tokenRepository.saveAll((Iterable<Token>) any())).thenReturn(new ArrayList<>());
        when(tokenRepository.findAllValidTokenByClient((Long) any())).thenReturn(tokenList);
        authenticationServiceImpl.revokeAllClientTokens(new Client());
        verify(tokenRepository).findAllValidTokenByClient((Long) any());
        verify(tokenRepository).saveAll((Iterable<Token>) any());
    }

    @Test
    void testRevokeAllClientTokens5() {
        Token token = mock(Token.class);
        doNothing().when(token).setExpired(anyBoolean());
        doNothing().when(token).setRevoked(anyBoolean());

        ArrayList<Token> tokenList = new ArrayList<>();
        tokenList.add(token);
        when(tokenRepository.saveAll((Iterable<Token>) any())).thenReturn(new ArrayList<>());
        when(tokenRepository.findAllValidTokenByClient((Long) any())).thenReturn(tokenList);
        authenticationServiceImpl.revokeAllClientTokens(new Client());
        verify(tokenRepository).findAllValidTokenByClient((Long) any());
        verify(tokenRepository).saveAll((Iterable<Token>) any());
        verify(token).setExpired(anyBoolean());
        verify(token).setRevoked(anyBoolean());
    }

    @Test
    void testSaveClientToken() {
        when(tokenRepository.save((Token) any())).thenReturn(new Token());
        authenticationServiceImpl.saveClientToken(new Client(), "ABC123");
        verify(tokenRepository).save((Token) any());
    }

    @Test
    void testSaveClientToken2() {
        when(tokenRepository.save((Token) any())).thenThrow(new NotOldEnough());
        assertThrows(NotOldEnough.class, () -> authenticationServiceImpl.saveClientToken(new Client(), "ABC123"));
        verify(tokenRepository).save((Token) any());
    }
}