package HealthAPI.exception;

import HealthAPI.messages.Responses;

public class ClientNotFound extends RuntimeException {

    public ClientNotFound() {
        super(Responses.CLIENT_NOT_FOUND);
    }

}