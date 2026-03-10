package com.globalhub.main.application.service;

import com.globalhub.main.domain.Student;
import com.globalhub.main.domain.events.user.UserRegisteredEvent;
import com.globalhub.main.domain.user.User;
import com.globalhub.main.domain.user.UserRole;
import com.globalhub.main.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleUserRegistration(UserRegisteredEvent event) {
        User savedUser = event.user();

        if (savedUser.getRole().equals(UserRole.STUDENT)) {
            Student newStudent = new Student();
            newStudent.setUser(savedUser);

            repository.save(newStudent);
        }
    }

}
