CREATE TABLE cart_item (
                           id BIGSERIAL,
                           product_id BIGINT REFERENCES public.products(id),
                           quantity INT NOT NULL,
    CONSTRAINT cart_item_id_pk PRIMARY KEY (id)
);
