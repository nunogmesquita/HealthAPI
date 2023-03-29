package HealthAPI.dto.timeSlot;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TimeSlotDto {

    private Long id;

    private LocalDateTime startTime;

    private String user;

    private boolean isBooked;

}