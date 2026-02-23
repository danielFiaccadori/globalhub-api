package com.globalhub.main.application.service;

import com.globalhub.main.application.adapters.mapper.TeamMapper;
import com.globalhub.main.application.dto.team.TeamCreationRequestDTO;
import com.globalhub.main.application.dto.team.TeamDetailsDTO;
import com.globalhub.main.application.dto.team.UpdateTeamRequestDTO;
import com.globalhub.main.domain.Course;
import com.globalhub.main.domain.Teacher;
import com.globalhub.main.domain.Team;
import com.globalhub.main.domain.exception.CourseNotFoundException;
import com.globalhub.main.domain.exception.PhantomTeamException;
import com.globalhub.main.domain.exception.TeamNotFoundException;
import com.globalhub.main.repository.CourseRepository;
import com.globalhub.main.repository.TeacherRepository;
import com.globalhub.main.repository.TeamRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final TeamMapper mapper;

    public TeamService(TeamRepository teamRepository, CourseRepository courseRepository, TeacherRepository teacherRepository, TeamMapper mapper) {
        this.teamRepository = teamRepository;
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.mapper = mapper;
    }

    public Page<TeamDetailsDTO> findAll(Pageable pageable) {
        Page<Team> page = teamRepository.findAll(pageable);
        return page.map(mapper::toTeamDetailsDTO);
    }

    public TeamDetailsDTO findById(UUID uuid) {
        Team team = teamRepository.findById(uuid)
                .orElseThrow(() -> new TeamNotFoundException(uuid));

        return mapper.toTeamDetailsDTO(team);
    }

    @Transactional
    public TeamDetailsDTO createTeam(TeamCreationRequestDTO teamCreationRequestData) {
        Course course = courseRepository.findById(teamCreationRequestData.courseId())
                .orElseThrow(() -> new CourseNotFoundException(teamCreationRequestData.courseId()));

        if (!course.getIsActive()) {
            throw new PhantomTeamException("Isn't possible to add a team to an inactive course!");
        }

        List<Teacher> teachers = teacherRepository.findAllById(teamCreationRequestData.teachers());

        Team team = new Team(
            teamCreationRequestData.name(), course, teachers
        );

        return mapper.toTeamDetailsDTO(team);
    }

    @Transactional
    public TeamDetailsDTO updateTeamDetails(UUID uuid, UpdateTeamRequestDTO teamUpdateRequestData) {
        Team toUpdateTeam = teamRepository.findById(uuid)
                .orElseThrow(() -> new TeamNotFoundException(uuid));
        Course course = null;

        if (teamUpdateRequestData.newCourseId() != null) {
            course = courseRepository.findById(teamUpdateRequestData.newCourseId())
                    .orElseThrow(() -> new CourseNotFoundException(teamUpdateRequestData.newCourseId()));
        }

        Optional.ofNullable(teamUpdateRequestData.newName()).ifPresent(toUpdateTeam::setName);
        Optional.ofNullable(course).ifPresent(toUpdateTeam::setCourse);

        return mapper.toTeamDetailsDTO(toUpdateTeam);
    }

    @Transactional
    public void updateActivity(UUID uuid, boolean activity) {
        Team toUpdateTeam = teamRepository.findById(uuid)
                .orElseThrow(() -> new TeamNotFoundException(uuid));

        toUpdateTeam.setIsActive(activity);
    }

}
