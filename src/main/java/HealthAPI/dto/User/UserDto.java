package HealthAPI.dto.User;

import HealthAPI.model.Speciality;
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

    private Speciality speciality;

}