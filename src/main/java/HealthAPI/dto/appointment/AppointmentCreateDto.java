package HealthAPI.dto.appointment;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentCreateDto {

    @NotNull
    private Long timeSlotId;

}