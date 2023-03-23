package HealthAPI.dto;

import HealthAPI.model.HealthCareSpecialty;
import HealthAPI.model.AppointmentType;
import HealthAPI.model.Client;
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
    @NotNull(message = "Choose your type for appointment")
    @Enumerated(EnumType.STRING)
    AppointmentType appointmentType;

}