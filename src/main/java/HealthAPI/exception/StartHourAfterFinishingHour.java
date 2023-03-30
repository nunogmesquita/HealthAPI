package HealthAPI.exception;

import HealthAPI.messages.Responses;

public class StartHourAfterFinishingHour extends RuntimeException {

    public StartHourAfterFinishingHour() {
        super(Responses.TIMESLOT_HOUR_ERROR);
    }

}