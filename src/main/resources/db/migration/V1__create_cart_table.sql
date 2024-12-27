CREATE TABLE IF NOT EXISTS cart
(
  id UUID     PRIMARY KEY,
  user_id     BIGINT,
  expire_date TIMESTAMP,
  CONSTRAINT id_expire_date_check CHECK
  (
    (user_id IS NULL AND expire_date IS NOT NULL) OR
    (user_id IS NOT NULL AND expire_date IS NULL)
 )
);
