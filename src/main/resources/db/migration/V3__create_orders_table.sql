CREATE TABLE orders (
                        id BIGSERIAL PRIMARY KEY,
                        user_id BIGINT,
                        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
