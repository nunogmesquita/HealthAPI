package HealthAPI.exception;

public class IdNotExist extends RuntimeException {

    public IdNotExist(String message) {
        super(message);
    }
}