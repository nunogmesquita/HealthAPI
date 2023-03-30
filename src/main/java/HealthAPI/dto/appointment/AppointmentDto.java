package HealthAPI.dto.appointment;

import HealthAPI.model.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto implements Serializable {

    @NotBlank
    private String professional;

    @NotBlank
    private String speciality;

    @NotBlank
    private String client;

    @NotNull
    private LocalDateTime date;

    @NotNull
    private Status status;

}