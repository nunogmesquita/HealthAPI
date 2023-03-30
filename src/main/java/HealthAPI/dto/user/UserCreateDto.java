package HealthAPI.dto.user;

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
    @Pattern(regexp = "^[A-Za-z]+$", message = "Please insert a valid first name.")
    private String firstName;

    @NotBlank(message = "Must have last name.")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Please insert a valid last name.")
    private String lastName;

    @NotBlank(message = "Must have email.")
    @Pattern(regexp = "^((?!\\.)[\\w-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$",
            message = "Please insert a valid email.")
    private String email;

    @NotBlank(message = "Must have password.")
    @Pattern(regexp = "^(?=.*[~!@#$%^&*()_+`\\-=\\[\\]\\{\\};':\\\",./<>?])(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])\\S{6,}$",
            message = "Password must have at least 8 characters: at least 1 uppercase letter, 1 lowercase letter, " +
                    "1 number and 1 special character.")
    private String password;

    private Speciality speciality;

    @NotNull(message = "Must have role.")
    private Role role;

}