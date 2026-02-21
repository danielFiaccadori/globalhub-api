package com.globalhub.main.domain.exception;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(Long id) {
        super("Course not found with ID: " + id);
    }

    public CourseNotFoundException(String message) {
        super(message);
    }
}
