package HealthAPI.dto.user;

import HealthAPI.model.Speciality;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private Speciality speciality;

}