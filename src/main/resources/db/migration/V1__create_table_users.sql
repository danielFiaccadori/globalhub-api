CREATE TABLE tb_users (
   id UUID NOT NULL,
   email VARCHAR(255) NOT NULL,
   password VARCHAR(100) NOT NULL,
   rgg VARCHAR(6) NOT NULL,
   role VARCHAR(50) NOT NULL,
   is_active BOOLEAN NOT NULL,

   CONSTRAINT pk_tb_users PRIMARY KEY (id),
   CONSTRAINT uc_users_email UNIQUE (email),
   CONSTRAINT uc_users_rgg UNIQUE (rgg)
);