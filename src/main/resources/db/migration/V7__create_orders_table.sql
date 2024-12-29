CREATE TABLE IF NOT EXISTS orders
(
    id             UUID PRIMARY KEY,
    user_email     VARCHAR(255),
    order_date     TIMESTAMP      NOT NULL DEFAULT NOW(),
    total_price    NUMERIC(10, 2) NOT NULL,
    payment_state  VARCHAR(50)    NOT NULL,
    shipment_state VARCHAR(50)    NOT NULL,
    state          VARCHAR(50)    NOT NULL,
    user_id        BIGINT,
    address_id     BIGINT         NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE SET NULL,
    CONSTRAINT fk_address FOREIGN KEY (address_id) REFERENCES addresses (id) ON DELETE CASCADE
);
