package HealthAPI.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private String dayOfWeek;

    @ManyToOne
    private User user;

    @OneToOne
    private Appointment appointment;

    private boolean isBooked;

    public DayOfWeek getDayOfWeek() {
        return startTime.getDayOfWeek();
    }

}