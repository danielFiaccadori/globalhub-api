CREATE TABLE tb_teachers (
    id UUID NOT NULL,
    user_id UUID NOT NULL,

    CONSTRAINT pk_fk_teachers PRIMARY KEY (id),
    CONSTRAINT fk_teachers_user_id FOREIGN KEY (user_id) REFERENCES tb_users(id) ON DELETE CASCADE,
    CONSTRAINT uc_teachers_user_id UNIQUE (user_id)
);