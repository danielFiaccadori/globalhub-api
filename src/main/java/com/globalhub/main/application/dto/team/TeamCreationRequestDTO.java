package com.globalhub.main.application.dto.team;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public record TeamCreationRequestDTO(String name, Long courseId, List<UUID> teachers,  DayOfWeek day, LocalTime time, Integer room) {
}
