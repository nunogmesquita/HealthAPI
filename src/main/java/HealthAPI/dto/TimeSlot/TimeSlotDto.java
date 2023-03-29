package HealthAPI.dto.TimeSlot;

import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TimeSlotDto {

    private LocalDateTime startTime;

    private String user;

    private boolean isBooked;

}