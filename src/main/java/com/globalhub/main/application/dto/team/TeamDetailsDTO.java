package com.globalhub.main.application.dto.team;

import com.globalhub.main.domain.Course;
import com.globalhub.main.domain.enrollment.Enrollment;

import java.util.List;

public record TeamDetailsDTO(List<Enrollment> enrollments, Course course) {
}
