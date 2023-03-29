package HealthAPI.exception;

import HealthAPI.messages.Responses;

public class AppointmentAlreadyActive extends RuntimeException {

    public AppointmentAlreadyActive(Long id) {
        super(Responses.APPOINTMENT_ALREADY_ACTIVE.formatted(id));
    }

}