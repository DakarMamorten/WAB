CREATE TABLE public.users
(
    id       BIGSERIAL ,
    name     VARCHAR(100) NOT NULL,
    email    VARCHAR(100) NOT NULL UNIQUE ,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(50)  NOT NULL,
    CONSTRAINT users_id_pk PRIMARY KEY (id)
);
