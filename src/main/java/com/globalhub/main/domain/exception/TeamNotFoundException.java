package com.globalhub.main.domain.exception;

import java.util.UUID;

public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException(UUID id) {
        super("Team not found with ID: " + id);
    }

    public TeamNotFoundException(String message) {
        super(message);
    }
}
