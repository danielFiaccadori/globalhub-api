package com.globalhub.main.domain.enrollment;

public enum EnrollmentStatus {

    ACTIVE("Active"),
    INACTIVE("Inactive"),
    PENDING("Pending");

    private final String description;

    EnrollmentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
