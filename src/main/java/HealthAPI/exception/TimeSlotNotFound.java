package HealthAPI.exception;

import HealthAPI.messages.Responses;

public class TimeSlotNotFound extends RuntimeException {

    public TimeSlotNotFound() {
        super(Responses.TIMESLOT_NOT_FOUND);
    }

}