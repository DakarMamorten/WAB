CREATE TABLE IF NOT EXISTS role
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS users
(
    id                  BIGSERIAL PRIMARY KEY,
    user_name           VARCHAR(100) NOT NULL,
    email               VARCHAR(50)  NOT NULL UNIQUE,
    password            VARCHAR(256) NOT NULL,
    lock_time           TIMESTAMP,
    enabled             BOOLEAN DEFAULT true,
    account_non_locked  BOOLEAN DEFAULT true,
    failed_attempt      INTEGER DEFAULT 0,
    role_id   BIGINT,
    cart_id   BIGINT,
    FOREIGN KEY (role_id) references role (id),
    FOREIGN KEY (cart_id) references cart (id)
);

CREATE INDEX idx_users_role_id ON users(role_id);
CREATE INDEX idx_users_cart_id ON users(cart_id);
