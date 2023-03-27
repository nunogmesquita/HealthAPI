package HealthAPI.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentCreateDto {

    Long userId;

    Long timeSlotId;

    Long clientId;

}