CREATE TABLE tb_users
(
    id                UUID                NOT NULL,
    profilePictureUrl TEXT,
    email             VARCHAR(255) UNIQUE NOT NULL,
    password          VARCHAR(100)        NOT NULL,
    rgg               VARCHAR(6)          NOT NULL,
    name              VARCHAR(255)        NOT NULL,
    cpf               VARCHAR(11) UNIQUE  NOT NULL,
    role              VARCHAR(50)         NOT NULL,
    is_active         BOOLEAN             NOT NULL,
    created_at        TIMESTAMP           NOT NULL,
    updated_at        TIMESTAMP,

    CONSTRAINT pk_tb_users PRIMARY KEY (id),
    CONSTRAINT uc_users_email UNIQUE (email),
    CONSTRAINT uc_users_rgg UNIQUE (rgg)
);