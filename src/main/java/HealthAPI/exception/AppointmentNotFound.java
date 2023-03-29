package HealthAPI.exception;

import HealthAPI.messages.Responses;

public class AppointmentNotFound extends RuntimeException {

    public AppointmentNotFound() {
        super(Responses.APPOINTMENT_NOT_FOUND);
    }

}