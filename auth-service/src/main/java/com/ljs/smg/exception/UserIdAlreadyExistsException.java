package com.ljs.smg.exception;

public class UserIdAlreadyExistsException extends RuntimeException {

    public UserIdAlreadyExistsException(String message) {
        super(message);
    }
}
