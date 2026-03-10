package com.globalhub.main.application.dto.team;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record UpdateTeamRequestDTO(String newName, Long newCourseId, Integer newRoom, DayOfWeek newDay, LocalTime newTime) {
}
