package com.globalhub.main.application.adapters.mapper;

import com.globalhub.main.application.dto.course.CourseDetailsDTO;
import com.globalhub.main.application.dto.team.TeamCreationRequestDTO;
import com.globalhub.main.application.dto.team.TeamDetailsDTO;
import com.globalhub.main.domain.Course;
import com.globalhub.main.domain.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeamMapper {

    TeamDetailsDTO toTeamDetailsDTO(Team team);

    CourseDetailsDTO toCourseDetailsDTO(Course course);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "teachers", ignore = true)
    @Mapping(target = "enrollments", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    Team toEntity(TeamCreationRequestDTO dto);

}
