package com.globalhub.main.application.adapters.mapper;

import com.globalhub.main.application.dto.teacher.TeacherDetailsDTO;
import com.globalhub.main.domain.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "user.name", target = "name")
    @Mapping(source = "user.rgg", target = "rgg")
    TeacherDetailsDTO toTeacherDetailsDTO(Teacher team);

}
