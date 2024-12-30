CREATE TABLE IF NOT EXISTS addresses
(
    id          BIGSERIAL PRIMARY KEY,
    first_name  VARCHAR(255) NOT NULL,
    last_name   VARCHAR(255) NOT NULL,
    street_name VARCHAR(255) NOT NULL,
    house       VARCHAR(50)  NOT NULL,
    flat        VARCHAR(50),
    postal_code VARCHAR(6)   NOT NULL,
    city        VARCHAR(255) NOT NULL,
    phone       VARCHAR(20)  NOT NULL,
    email       VARCHAR(255) NOT NULL UNIQUE
);

CREATE INDEX idx_city ON addresses (phone);
