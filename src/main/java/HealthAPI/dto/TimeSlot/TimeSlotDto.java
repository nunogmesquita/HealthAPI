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

    private LocalDateTime endTime;

    private DayOfWeek dayOfWeek;

    private String user;

    private boolean isBooked;

}