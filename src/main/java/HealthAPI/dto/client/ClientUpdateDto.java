package HealthAPI.dto.client;

import HealthAPI.model.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientUpdateDto {

    @Pattern(regexp = "^[A-Za-z]+$", message = "Please insert a valid name.")
    private String fullName;

    @Pattern(regexp = "^((?!\\.)[\\w-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$",
            message = "Please insert a valid email.")
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

    private AddressDto addressDto;

}