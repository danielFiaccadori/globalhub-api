package com.globalhub.main.application.service;

import com.globalhub.main.application.adapters.mapper.CourseMapper;
import com.globalhub.main.application.dto.course.CourseCreationRequestDTO;
import com.globalhub.main.application.dto.course.CourseDetailsDTO;
import com.globalhub.main.application.dto.course.UpdateCourseRequestDTO;
import com.globalhub.main.domain.Course;
import com.globalhub.main.domain.exception.CourseNotFoundException;
import com.globalhub.main.repository.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository repository;
    private final CourseMapper mapper;

    public CourseService(CourseRepository repository, CourseMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Page<CourseDetailsDTO> findAll(Pageable pageable) {
        Page<Course> page = repository.findAll(pageable);
        return page.map(mapper::toCourseDetailsDTO);
    }

    public CourseDetailsDTO findById(Long id) {
        Course course = repository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
        return mapper.toCourseDetailsDTO(course);
    }

    public CourseDetailsDTO createCourse(CourseCreationRequestDTO creationRequestData) {
        Course course = new Course(
                creationRequestData.name(),
                creationRequestData.description()
        );

        repository.save(course);
        return mapper.toCourseDetailsDTO(course);
    }

    @Transactional
    public CourseDetailsDTO updateCourseDetails(Long id, UpdateCourseRequestDTO updateCourseRequestData) {
        Course toUpdateCourse = repository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));

        Optional.ofNullable(updateCourseRequestData.newName()).ifPresent(toUpdateCourse::setName);
        Optional.ofNullable(updateCourseRequestData.newDescription()).ifPresent(toUpdateCourse::setDescription);

        return mapper.toCourseDetailsDTO(toUpdateCourse);
    }

    @Transactional
    public void updateActivity(Long id, boolean activity) {
        Course toUpdateCourse = repository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));

        if (!activity && toUpdateCourse.getTeams().isEmpty()) {
            repository.delete(toUpdateCourse);
        } else {
            toUpdateCourse.setIsActive(activity);
        }
    }

}
