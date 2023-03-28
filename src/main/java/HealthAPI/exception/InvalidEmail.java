package HealthAPI.exception;

import HealthAPI.messages.Responses;

public class InvalidEmail extends RuntimeException {

    public InvalidEmail() {
        super(Responses.INVALID_EMAIL);
    }
}