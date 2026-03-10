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
import com.globalhub.main.domain.exception.ScheduleConflictException;
import com.globalhub.main.domain.exception.TeamNotFoundException;
import com.globalhub.main.repository.CourseRepository;
import com.globalhub.main.repository.TeacherRepository;
import com.globalhub.main.repository.TeamRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
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

    public Page<TeamDetailsDTO> findAllByTeacher(UUID teacherUuid, Pageable pageable) {
        Page<Team> page = teamRepository.findAllByTeacherUuid(teacherUuid, pageable);
        return page.map(mapper::toTeamDetailsDTO);
    }

    public Page<TeamDetailsDTO> findAllByDay(DayOfWeek day, Pageable pageable) {
        Page<Team> page = teamRepository.findAllByDay(day, pageable);
        return page.map(mapper::toTeamDetailsDTO);
    }

    public TeamDetailsDTO findById(UUID uuid) {
        Team team = teamRepository.findById(uuid)
                .orElseThrow(() -> new TeamNotFoundException(uuid));

        return mapper.toTeamDetailsDTO(team);
    }

    public List<Integer> findOccupiedRooms(String day, String time) {
        DayOfWeek parsedDay = DayOfWeek.valueOf(day.toUpperCase());
        LocalTime parsedTime = LocalTime.parse(time);
        List<Integer> occupiedRooms = teamRepository.findOccupiedRooms(parsedDay, parsedTime);

        return occupiedRooms;
    }

    @Transactional
    public TeamDetailsDTO createTeam(TeamCreationRequestDTO teamCreationRequestData) {
        Course course = courseRepository.findById(teamCreationRequestData.courseId())
                .orElseThrow(() -> new CourseNotFoundException(teamCreationRequestData.courseId()));

        if (!course.getIsActive()) {
            throw new PhantomTeamException("É impossível adicionar um time à um curso inativo!");
        }

        List<Teacher> teachers = teacherRepository.findAllById(teamCreationRequestData.teachers());

        Team team = mapper.toEntity(teamCreationRequestData);

        team.setTeachers(teachers);
        team.setCourse(course);

        for (UUID teacherId : teamCreationRequestData.teachers()) {
            boolean isBusy = teamRepository.hasScheduleConflict(teacherId, teamCreationRequestData.day(), teamCreationRequestData.time());

            if (isBusy) {
                throw new ScheduleConflictException("Um professor selecionado já possui uma turma agendada nesse horário!");
            }
        }

        teamRepository.save(team);
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


        DayOfWeek targetDay = teamUpdateRequestData.newDay() != null ? teamUpdateRequestData.newDay() : toUpdateTeam.getDay();
        LocalTime targetTime = teamUpdateRequestData.newTime() != null ? teamUpdateRequestData.newTime() : toUpdateTeam.getTime();
        Integer targetRoom = teamUpdateRequestData.newRoom() != null ? teamUpdateRequestData.newRoom() : toUpdateTeam.getRoom();

        boolean hasRoomConflict = teamRepository.hasRoomConflictForUpdate(targetRoom, targetDay, targetTime, uuid);
        if (hasRoomConflict) {
            throw new ScheduleConflictException("A sala " + targetRoom + " já está ocupada neste horário.");
        }

        toUpdateTeam.getTeachers().forEach(teacher -> {
            boolean hasTeacherConflict = teamRepository.hasTeacherConflictForUpdate(teacher.getId(), targetDay, targetTime, uuid);
            if (hasTeacherConflict) {
                throw new ScheduleConflictException("O professor " + teacher.getUser().getName() + " já possui outra aula neste dia e horário.");
            }
        });

        Optional.ofNullable(teamUpdateRequestData.newName()).ifPresent(toUpdateTeam::setName);
        Optional.ofNullable(course).ifPresent(toUpdateTeam::setCourse);
        Optional.ofNullable(teamUpdateRequestData.newRoom()).ifPresent(toUpdateTeam::setRoom);
        Optional.ofNullable(teamUpdateRequestData.newDay()).ifPresent(toUpdateTeam::setDay);
        Optional.ofNullable(teamUpdateRequestData.newTime()).ifPresent(toUpdateTeam::setTime);

        return mapper.toTeamDetailsDTO(toUpdateTeam);
    }

    @Transactional
    public void updateActivity(UUID uuid, boolean activity) {
        Team toUpdateTeam = teamRepository.findById(uuid)
                .orElseThrow(() -> new TeamNotFoundException(uuid));

        toUpdateTeam.setIsActive(activity);
    }

}
