package com.pwnned.domain.exception;

public class UserAlreadyPremiumException extends RuntimeException {
    public UserAlreadyPremiumException(String message) {
        super("[ERROR] " + message);
    }
}
