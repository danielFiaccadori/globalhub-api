package com.globalhub.main.domain.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID uuid) {
        super("Customer not found with UUID: " + uuid);
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
