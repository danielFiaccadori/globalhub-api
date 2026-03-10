CREATE TABLE tb_teams
(
    id        UUID         NOT NULL,
    name      VARCHAR(255) NOT NULL,
    course_id BIGINT       NOT NULL,
    time      TIME         NOT NULL,
    day       VARCHAR(10)  NOT NULL,
    room      INTEGER      NOT NULL,
    is_active BOOLEAN      NOT NULL,

    CONSTRAINT pk_table_teams PRIMARY KEY (id),
    CONSTRAINT fk_teams_course_id FOREIGN KEY (course_id) REFERENCES tb_courses (id) ON DELETE CASCADE
);