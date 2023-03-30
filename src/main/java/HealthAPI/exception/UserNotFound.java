package HealthAPI.exception;

import HealthAPI.messages.Responses;

public class UserNotFound extends RuntimeException {

    public UserNotFound() {
        super(Responses.USER_NOT_FOUND);
    }

}