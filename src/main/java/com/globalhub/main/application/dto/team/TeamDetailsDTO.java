package com.globalhub.main.application.dto.team;

import com.globalhub.main.application.dto.course.CourseDetailsDTO;
import com.globalhub.main.domain.enrollment.Enrollment;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public record TeamDetailsDTO(UUID id, List<Enrollment> enrollments, CourseDetailsDTO course, String name, Integer room, DayOfWeek day, LocalTime time) {
}
