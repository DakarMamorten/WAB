CREATE TABLE cart_item
(
    id           BIGSERIAL,
    product_name VARCHAR(255),
    quantity     INT NOT NULL,
    cart_id      BIGINT,
    CONSTRAINT cart_item_id_pk PRIMARY KEY (id),
    FOREIGN KEY (cart_id) references cart (id)
);

CREATE INDEX idx_cart_item_id ON cart_item (cart_id);