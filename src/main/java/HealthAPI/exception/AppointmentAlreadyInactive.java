package HealthAPI.exception;

import HealthAPI.messages.Responses;

public class AppointmentAlreadyInactive extends RuntimeException {

    public AppointmentAlreadyInactive(Long id) {
        super(Responses.APPOINTMENT_ALREADY_INACTIVE.formatted(id));
    }

}