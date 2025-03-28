package com.pwnned.domain.exception;

public class UserTypeException extends RuntimeException {
    public UserTypeException(String message) {
        super("[ERROR] " + message);
    }
}
