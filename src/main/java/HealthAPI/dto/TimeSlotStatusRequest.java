package HealthAPI.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TimeSlotStatusRequest {

    @NotNull
    private Long hcpId;

    @NotNull
    private Long clientId;

    @NotNull
    private Date currentDate;
}
