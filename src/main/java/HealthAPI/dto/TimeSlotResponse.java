package HealthAPI.dto;

import HealthAPI.model.TimeSlot;

import java.util.List;

public class TimeSlotResponse extends BaseResponse<List<TimeSlot>> {

    public TimeSlotResponse(List<TimeSlot> data, String message) {
        super(data, message);
    }
}
