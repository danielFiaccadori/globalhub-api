package com.globalhub.main.repository;

import com.globalhub.main.domain.Student;
import com.globalhub.main.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {


}
