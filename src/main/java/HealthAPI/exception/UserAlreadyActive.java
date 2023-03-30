package HealthAPI.exception;

import HealthAPI.messages.Responses;

public class UserAlreadyActive extends RuntimeException {

    public UserAlreadyActive(Long id) {
        super(Responses.USER_ALREADY_ACTIVE.formatted(id));
    }

}