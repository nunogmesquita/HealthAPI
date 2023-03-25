package HealthAPI.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AuthenticationRequest {

    @NotBlank(message = "Must have email.")
    String email;

    @NotBlank(message = "Must have password.")
    private String password;

}