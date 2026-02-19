CREATE TABLE tb_students (
    id UUID NOT NULL,
    user_id UUID NOT NULL,

    CONSTRAINT pk_fk_students PRIMARY KEY (id),
    CONSTRAINT fk_students_user_id FOREIGN KEY (user_id) REFERENCES tb_users(id) ON DELETE CASCADE,
    CONSTRAINT uc_students_user_id UNIQUE (user_id)
);