package HealthAPI.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "slots")
@Entity
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @NotNull
    private Date startTime; // 24hr format.

    @NotNull
    private Date endTime;

    @NotNull
    private Long hcpId;

    private boolean isBooked;

    private boolean isBookedBySameClient;

}