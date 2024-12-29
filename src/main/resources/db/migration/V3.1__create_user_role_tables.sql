CREATE TABLE IF NOT EXISTS role
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS users
(
    id                  BIGSERIAL PRIMARY KEY,
    first_name          VARCHAR(100) NOT NULL,
    last_name           VARCHAR(100) NOT NULL,
    email               VARCHAR(50)  NOT NULL UNIQUE,
    password            VARCHAR(256) NOT NULL,
    lock_time           TIMESTAMP,
    enabled             BOOLEAN DEFAULT true,
    account_non_locked  BOOLEAN DEFAULT true,
    failed_attempt      INTEGER DEFAULT 0,
    role_id             BIGINT,
    address_id          BIGINT,
    FOREIGN KEY (role_id) references role (id),
    FOREIGN KEY (address_id) references addresses (id)
);

CREATE INDEX idx_users_role_id ON users(role_id);
CREATE INDEX idx_users_address_id ON users(address_id);
CREATE INDEX idx_users_email ON users(email);
