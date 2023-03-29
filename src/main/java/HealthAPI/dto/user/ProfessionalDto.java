package HealthAPI.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfessionalDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String speciality;

}