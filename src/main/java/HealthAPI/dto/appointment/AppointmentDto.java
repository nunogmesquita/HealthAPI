package HealthAPI.dto.appointment;

import HealthAPI.model.Status;
import lombok.*;
import org.springframework.cache.annotation.Cacheable;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto implements Serializable {

    private String professional;

    private String speciality;

    private String client;

    private LocalDateTime date;

    private Status status;

}