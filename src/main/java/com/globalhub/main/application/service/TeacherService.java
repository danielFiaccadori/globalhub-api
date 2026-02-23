package com.globalhub.main.application.service;

import com.globalhub.main.domain.Teacher;
import com.globalhub.main.domain.events.user.UserRegisteredEvent;
import com.globalhub.main.domain.user.User;
import com.globalhub.main.domain.user.UserRole;
import com.globalhub.main.repository.TeacherRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {

    private final TeacherRepository repository;

    public TeacherService(TeacherRepository repository) {
        this.repository = repository;
    }

    @EventListener
    public void handleUserRegistration(UserRegisteredEvent event) {
        User savedUser = event.user();

        if (savedUser.getRole().equals(UserRole.TEACHER)) {
            Teacher newTeacher = new Teacher();
            newTeacher.setUser(savedUser);

            repository.save(newTeacher);
        }
    }

}
