package HealthAPI.dto.User;

import HealthAPI.model.Role;
import HealthAPI.model.Speciality;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto {

    @NotBlank(message = "Must have first name.")
    String firstName;

    @NotBlank(message = "Must have last name.")
    String lastName;

    @NotBlank(message = "Must have email.")
    @Pattern(regexp = "^((?!\\.)[\\w-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$",
            message = "Please insert a valid email.")
    String email;

    @NotBlank(message = "Must have password.")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,16}$",
            message = "Password must contain at least 1 number (0-9),  1 uppercase letter,  " +
                    "1 lowercase letter, 1 non-alpha numeric number and have 8-16 characters with no space")
    private String password;

    Speciality speciality;

    @NotNull(message = "Must have role.")
    Role role;

}