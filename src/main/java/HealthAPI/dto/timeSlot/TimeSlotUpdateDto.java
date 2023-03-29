package HealthAPI.dto.timeSlot;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TimeSlotUpdateDto {

    private LocalDateTime time;

}