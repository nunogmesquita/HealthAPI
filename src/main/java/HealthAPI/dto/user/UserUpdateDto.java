package HealthAPI.dto.user;

import HealthAPI.model.Speciality;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {

    private String firstName;

    private String lastName;

    @Pattern(regexp = "^((?!\\.)[\\w-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$",
            message = "Please insert a valid email.")
    private String email;

    @Pattern(regexp = "^((?=\\\\S*?[A-Z])(?=\\\\S*?[a-z])(?=\\\\S*?[0-9]).{8,})\\\\S$",
            message = "Password must have at least 8 characters: at least 1 uppercase letter, 1 lowercase letter, " +
                    "and 1 number with no spaces.")
    private String password;

    private Speciality speciality;

}