CREATE TABLE IF NOT EXISTS cart_item
(
    cart_id    UUID NOT NULL,
    product_id BIGINT NOT NULL,
    quantity   INT NOT NULL,
    PRIMARY KEY (cart_id, product_id),
    FOREIGN KEY (cart_id) references cart (id),
    FOREIGN KEY (product_id) references products (id)
);

CREATE INDEX idx_cart_item_cart_id ON cart_item (cart_id);
CREATE INDEX idx_cart_item_product_id ON cart_item (product_id);
