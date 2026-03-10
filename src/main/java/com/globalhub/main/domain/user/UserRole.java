package com.globalhub.main.domain.user;

public enum UserRole {

    SUPER_ADMIN("Super admin"),
    ADMIN("Admin"),
    TEACHER("Teacher"),
    STUDENT("Student");

    private final String description;

    UserRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
