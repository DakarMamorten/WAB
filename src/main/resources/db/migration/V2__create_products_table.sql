CREATE TABLE public.products
(
    id          BIGSERIAL,
    name        VARCHAR(255)   NOT NULL,
    description TEXT,
    price       DECIMAL(10, 2) NOT NULL,
    brand       VARCHAR(100)   NOT NULL,
    CONSTRAINT products_id_pk PRIMARY KEY (id)
);
