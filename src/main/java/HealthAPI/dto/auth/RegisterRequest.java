package HealthAPI.dto.auth;

import HealthAPI.dto.client.AddressDto;
import HealthAPI.model.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Must have name.")
    private String fullName;

    @NotBlank(message = "Must have email.")
    @Pattern(regexp = "^((?!\\.)[\\w-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$",
            message = "Please insert a valid email.")
    private String email;

    @NotBlank(message = "Must have password.")
    @Pattern(regexp = "^(?=.*[~!@#$%^&*()_+`\\-=\\[\\]\\{\\};':\\\",./<>?])(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])\\S{6,}$",
            message = "Password must have at least 8 characters: at least 1 uppercase letter, 1 lowercase letter, " +
                    "1 number and 1 special character.")
    private String password;

    @NotNull(message = "Must have phone number.")
    private int phoneNumber;

    @NotNull(message = "Must have birth date.")
    private LocalDate birthDate;

    @NotNull(message = "Must have gender.")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private int NIF;

    private AddressDto address;

}