package HealthAPI.exception;

import HealthAPI.messages.Responses;

public class TimeSlotNotFound extends RuntimeException {

    public TimeSlotNotFound(Long id) {
        super(Responses.TIMESLOT_NOT_FOUND.formatted(id));
    }

}