CREATE TABLE orders (
                        id BIGSERIAL,
                        user_id BIGINT NOT NULL,
                        price DECIMAL(10, 2) NOT NULL,
                        brand VARCHAR(100) NOT NULL,
                        CONSTRAINT order_id_pk PRIMARY KEY (id),
                        CONSTRAINT order_user_id_fk FOREIGN KEY (user_id) REFERENCES users(id)
);
