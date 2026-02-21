package com.globalhub.main.application.adapters.mapper;

import com.globalhub.main.application.dto.course.CourseDetailsDTO;
import com.globalhub.main.application.dto.user.UserDetailsDTO;
import com.globalhub.main.domain.Course;
import com.globalhub.main.domain.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseDetailsDTO toCourseDetailsDTO(Course course);

}
