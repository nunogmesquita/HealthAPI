package HealthAPI.dto.timeSlot;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    private LocalDate date;

    @NotBlank
    private LocalTime initialHour;

    @NotBlank
    private LocalTime finishingHour;

    @NotBlank
    private List<DayOfWeek> dayOfWeeks;

    private List<String> excludedTimeRanges;

    @NotBlank
    private Long userId;

}