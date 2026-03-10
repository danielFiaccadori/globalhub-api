package com.globalhub.main.domain.exception;

public class ScheduleConflictException extends RuntimeException {
    public ScheduleConflictException() {
        super("This time is already booked for this teacher!");
    }

    public ScheduleConflictException(String message) {
        super(message);
    }
}
