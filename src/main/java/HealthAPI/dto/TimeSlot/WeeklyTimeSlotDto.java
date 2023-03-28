package HealthAPI.dto.TimeSlot;

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
    LocalDate date;

    @NotBlank
    LocalTime initialHour;

    @NotBlank
    LocalTime finishingHour;

    @NotBlank
    List<DayOfWeek> dayOfWeeks;

    List<String> excludedTimeRanges;

    @NotBlank
    Long userId;

}