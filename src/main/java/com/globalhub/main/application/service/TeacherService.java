package com.globalhub.main.application.service;

import com.globalhub.main.application.adapters.mapper.TeacherMapper;
import com.globalhub.main.application.dto.teacher.TeacherDetailsDTO;
import com.globalhub.main.domain.Teacher;
import com.globalhub.main.domain.events.user.UserRegisteredEvent;
import com.globalhub.main.domain.user.User;
import com.globalhub.main.domain.user.UserRole;
import com.globalhub.main.repository.TeacherRepository;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class TeacherService {

    private final TeacherRepository repository;
    private final TeacherMapper mapper;

    public TeacherService(TeacherRepository repository, TeacherMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleUserRegistration(UserRegisteredEvent event) {
        User savedUser = event.user();

        if (savedUser.getRole().equals(UserRole.TEACHER)) {
            Teacher newTeacher = new Teacher();
            newTeacher.setUser(savedUser);

            repository.save(newTeacher);
        }
    }

    public Page<TeacherDetailsDTO> findAll(Pageable pageable) {
        Page<Teacher> teachers = repository.findAll(pageable);
        return teachers.map(mapper::toTeacherDetailsDTO);
    }

}
