package HealthAPI.dto.TimeSlot;

import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TimeSlotUpdateDto {

    private LocalDateTime time;

    private DayOfWeek dayOfWeek;

    private int month;

    private int year;
}