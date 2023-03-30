package HealthAPI.exception;

import HealthAPI.messages.Responses;

public class TimeSlotAlreadyBooked extends RuntimeException {

    public TimeSlotAlreadyBooked(Long id) {
        super(Responses.TIMESLOT_ALREADY_BOOKED.formatted(id));
    }

}