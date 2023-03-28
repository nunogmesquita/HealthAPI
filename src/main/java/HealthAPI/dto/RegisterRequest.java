package HealthAPI.dto;

import HealthAPI.model.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDate;

@Data
@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Must have name.")
    private String fullName;

    @NotBlank(message = "Must have email.")
    @Pattern(regexp = "^((?!\\.)[\\w-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$", message = "Please insert a valid email.")
    private String email;

    @NotEmpty
    @Size(min = 8, message = "Password should have at least 8 characters")
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