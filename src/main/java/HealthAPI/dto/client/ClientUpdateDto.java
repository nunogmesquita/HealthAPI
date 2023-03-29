package HealthAPI.dto.client;

import HealthAPI.model.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientUpdateDto {

    private String fullName;

    private String email;

    @Pattern(regexp = "^(?=.*[~!@#$%^&*()_+`\\-=\\[\\]\\{\\};':\\\",./<>?])(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])\\S{6,}$",
            message = "Password must have at least 8 characters: at least 1 uppercase letter, 1 lowercase letter, " +
                    "1 number and 1 special character.")
    private String password;

    private int phoneNumber;

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private int NIF;

    private AddressDto address;

}