package HealthAPI.dto.timeSlot;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TimeSlotDto implements Serializable {

    private Long id;

    private LocalDateTime startTime;

    private String user;

    private boolean isBooked;

}