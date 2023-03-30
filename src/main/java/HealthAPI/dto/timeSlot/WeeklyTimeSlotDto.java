package HealthAPI.dto.timeSlot;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WeeklyTimeSlotDto {

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalTime initialHour;

    @NotNull
    private LocalTime finishingHour;

    @NotNull
    private List<DayOfWeek> dayOfWeeks;

    private List<String> excludedTimeRanges;

    @NotNull
    private Long userId;

}