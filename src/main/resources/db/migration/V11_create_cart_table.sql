CREATE TABLE cart (
                      id BIGSERIAL PRIMARY KEY,
                      user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE
);
