CREATE TABLE IF NOT EXISTS order_item
(
    order_id               UUID    NOT NULL,
    product_id             BIGINT  NOT NULL,
    quantity               INT     NOT NULL,
    price_at_time_of_order NUMERIC NOT NULL,
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) references orders (id),
    FOREIGN KEY (product_id) references products (id)
);

CREATE INDEX idx_order_item_order_id ON order_item (order_id);
CREATE INDEX idx_order_item_product_id ON order_item (product_id);
