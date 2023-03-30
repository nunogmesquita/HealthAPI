package HealthAPI.exception;

import HealthAPI.messages.Responses;

public class InvalidNIF extends RuntimeException {

    public InvalidNIF() {
        super(Responses.INVALID_NIF);
    }

}