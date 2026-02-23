package com.globalhub.main.domain.exception;

public class PhantomTeamException extends RuntimeException {
    public PhantomTeamException() {
        super("Someone attempted to create a Team without a Course!");
    }

    public PhantomTeamException(String message) {
        super(message);
    }
}
