package HealthAPI.exception;

import HealthAPI.messages.Responses;
import org.aspectj.bridge.Message;

public class NotOldEnough extends RuntimeException {

    public NotOldEnough() {
        super(Responses.AGE);
    }
}