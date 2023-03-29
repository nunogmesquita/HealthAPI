package HealthAPI.dto.User;

import HealthAPI.model.Speciality;
import jakarta.annotation.Nullable;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    @Nullable
    private Speciality speciality;

}