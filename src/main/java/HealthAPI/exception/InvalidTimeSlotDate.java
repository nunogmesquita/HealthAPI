package HealthAPI.exception;

import HealthAPI.messages.Responses;

public class InvalidTimeSlotDate extends RuntimeException {

    public InvalidTimeSlotDate() {
        super(Responses.INVALID_TIMESLOT_DATE);
    }

}