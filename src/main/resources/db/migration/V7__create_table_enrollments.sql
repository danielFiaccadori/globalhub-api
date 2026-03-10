CREATE TABLE tb_enrollments
(
    id                UUID         NOT NULL,
    enrollment_status VARCHAR(100) NOT NULL,
    creation_date     TIMESTAMP    NOT NULL,


    student_id        UUID         NOT NULL,
    team_id           UUID         NOT NULL,

    CONSTRAINT pk_tb_enrollments PRIMARY KEY (id),
    CONSTRAINT fk_enrollments_student_id FOREIGN KEY (student_id) REFERENCES tb_students (id) ON DELETE CASCADE,
    CONSTRAINT fk_enrollments_team_id FOREIGN KEY (team_id) REFERENCES tb_teams (id) ON DELETE CASCADE
);