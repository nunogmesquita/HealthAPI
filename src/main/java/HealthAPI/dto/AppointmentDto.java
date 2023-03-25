package HealthAPI.dto;

import HealthAPI.model.TimeSlot;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDto {
    @NotNull(message = "You meed to choose a schedule.")
    TimeSlot timeSlot;
    @NotNull(message = "Must have a client associated.")
    private Long clientId;

}