package HealthAPI.exception;

import HealthAPI.messages.Responses;

public class UnauthorizedAction extends RuntimeException {

    public UnauthorizedAction() {
        super(Responses.UNAUTHORIZED_ACTION);
    }

}