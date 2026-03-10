package com.globalhub.main.application.adapters.mapper;

import com.globalhub.main.application.dto.enrollment.EnrollmentDetailsDTO;
import com.globalhub.main.domain.enrollment.Enrollment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {

    @Mapping(source = "student.user.name", target = "studentName")
    @Mapping(source = "enrollmentStatus", target = "status")
    EnrollmentDetailsDTO toEnrollmentDetailsDTO(Enrollment course);

}
