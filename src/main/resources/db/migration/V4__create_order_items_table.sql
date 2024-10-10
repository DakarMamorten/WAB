CREATE TABLE public.order_item
(
    id         BIGSERIAL,
    order_id   BIGINT,
    product_id BIGINT,
    quantity   INT            NOT NULL,
    price      DECIMAL(10, 2) NOT NULL,
    CONSTRAINT order_items_order__pk PRIMARY KEY (id),
    CONSTRAINT order_items_order_fk FOREIGN KEY (order_id) REFERENCES orders (id),
    CONSTRAINT order_items_product_fk FOREIGN KEY (product_id) REFERENCES products (id)
);

