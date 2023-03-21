package HealthAPI.dto;

import lombok.Getter;

@Getter
public class AuthenticationRequest {

    private String email;

    String password;

}