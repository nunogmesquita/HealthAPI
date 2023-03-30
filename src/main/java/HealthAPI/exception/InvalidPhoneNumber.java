package HealthAPI.exception;

import HealthAPI.messages.Responses;

public class InvalidPhoneNumber extends RuntimeException {

    public InvalidPhoneNumber() {
        super(Responses.INVALID_PHONE_NUMBER.formatted());
    }

}