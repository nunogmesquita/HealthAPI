package HealthAPI.dto.appointment;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentCreateDto {

    private Long userId;

    private Long timeSlotId;

}