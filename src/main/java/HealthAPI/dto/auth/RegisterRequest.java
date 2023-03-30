package HealthAPI.dto.auth;

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
    @Pattern(regexp = "^[A-Za-z]+$", message = "Please insert a valid name.")
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

    @NotNull(message = "Must have NIF.")
    private int NIF;

    @NotBlank(message = "Must have a street.")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Please insert a valid street.")
    private String street;

    @NotBlank(message = "Must have a city.")
    @Pattern(regexp = "^[A-Za-z,]+$", message = "Please insert a valid city.")
    private String city;

    @NotBlank(message = "Must have a zip code")
    @Pattern(regexp = "^\\d{4}(-\\d{3})?$",
            message = "Please insert a valid zipcode.")
    private String zipCode;

}