package HealthAPI.exception;

import HealthAPI.messages.Responses;

public class NotOldEnough extends RuntimeException {

    public NotOldEnough() {
        super(Responses.AGE);
    }

}