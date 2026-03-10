CREATE TABLE tb_courses
(
    id          BIGSERIAL    NOT NULL,
    name        VARCHAR(255) NOT NULL,
    description TEXT         NOT NULL,
    is_active   BOOLEAN      NOT NULL,

    CONSTRAINT pk_table_courses PRIMARY KEY (id)
);