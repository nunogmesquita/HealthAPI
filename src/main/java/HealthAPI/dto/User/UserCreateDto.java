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
    private String password;

    Speciality speciality;

    @NotNull(message = "Must have role.")
    Role role;

}