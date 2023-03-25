package HealthAPI.dto.Client;

import HealthAPI.model.address.Address;
import HealthAPI.model.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientCreateDto {

    @NotBlank(message = "Must have name.")
    String fullName;

    @NotBlank(message = "Must have phone number.")
    @Pattern(regexp = "^([9][1236])[0-9]*$", message = "Please insert a valid phone number.")
    int phoneNumber;

    @NotBlank(message = "Must have email.")
    @Pattern(regexp = "^((?!\\.)[\\w-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$", message = "Please insert a valid email.")
    String email;

    @NotBlank(message = "Must have password.")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,16}$",
            message = "Password must contain at least 1 number (0-9),  1 uppercase letter,  1 lowercase letter, " +
                    "1 non-alpha numeric number and have 8-16 characters with no space")
    String password;

    @NotBlank(message = "Must have birth date.")
    @Pattern(regexp = "^((2000|2400|2800|(19|2[0-9])(0[48]|[2468][048]|[13579][26]))-02-29)$"
            + "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
            + "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"
            + "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$", message = "Invalid date.")
    private LocalDate birthDate;

    @NotBlank(message = "Must have gender.")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotBlank(message = "Must have NIF.")
    @Pattern(regexp = "^(?:9[1-36][0-9]|2[12][0-9]|23[1-689]|24[1-59]|25[1-9]|26[1-35689]|27[1-9]|28[1-69]|29[1256]|30[0-9])[0-9]{6}$",
            message = "Invalid NIF.")
    private int NIF;

    private Address address;

}