CREATE TABLE reviews (
                         id BIGSERIAL PRIMARY KEY,
                         product_id BIGINT NOT NULL REFERENCES products(id),
                         user_id BIGINT NOT NULL REFERENCES users(id),
                         comment TEXT NOT NULL,
                         rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
