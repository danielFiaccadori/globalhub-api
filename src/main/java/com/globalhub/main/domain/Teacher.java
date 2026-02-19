package com.globalhub.main.domain;

import com.globalhub.main.domain.user.User;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tb_teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

}