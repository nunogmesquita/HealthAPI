package HealthAPI.exception;

import HealthAPI.messages.Responses;

public class TooOld extends RuntimeException {

    public TooOld(int age) {
        super(Responses.TOO_OLD.formatted(age));
    }

}