package HealthAPI.dto.appointment;

import HealthAPI.model.Status;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto {

    private String professional;

    private String speciality;

    private String client;

    private LocalDateTime date;

    private Status status;

}