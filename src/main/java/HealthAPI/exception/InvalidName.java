package HealthAPI.exception;

import HealthAPI.messages.Responses;

public class InvalidName extends RuntimeException {

    public InvalidName(String name) {
        super(Responses.INVALID_NAME.formatted(name));
    }

}