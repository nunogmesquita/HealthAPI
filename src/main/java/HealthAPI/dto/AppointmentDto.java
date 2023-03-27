package HealthAPI.dto;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto {

    String professional;

    String speciality;

    int year;

    int month;

    String hour;

    String client;

}