package HealthAPI.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class TimeSlotBookingRequest {

    @NotNull
    private Long hcpId;

    @NotNull
    private Long timeSlotId;

    @NotNull
    private Date bookingDate; // Parse only the Date not the time.

    @NotNull
    private Long clientId; // User for reference to the user.
}
