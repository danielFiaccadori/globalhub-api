package com.globalhub.main.application.dto.enrollment;

import com.globalhub.main.domain.enrollment.EnrollmentStatus;

import java.util.UUID;

public record EnrollmentDetailsDTO(UUID id, String studentName, EnrollmentStatus status) {
}