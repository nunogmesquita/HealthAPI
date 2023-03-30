package HealthAPI.exception;

import HealthAPI.messages.Responses;

public class TimeSlotsAlreadyExists extends RuntimeException {

    public TimeSlotsAlreadyExists() {
        super(Responses.TIMESLOTS_ALREADY_EXISTS);
    }

}