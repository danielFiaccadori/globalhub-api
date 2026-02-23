CREATE TABLE tb_teams_teachers (
    team_id UUID NOT NULL,
    teacher_id UUID NOT NULL,

    CONSTRAINT pk_tb_teams_teachers PRIMARY KEY (team_id, teacher_id),
    CONSTRAINT fk_tt_team_id FOREIGN KEY (team_id) REFERENCES tb_teams(id) ON DELETE CASCADE,
    CONSTRAINT fk_tt_teacher_id FOREIGN KEY (teacher_id) REFERENCES tb_teachers(id) ON DELETE CASCADE
);