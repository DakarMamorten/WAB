INSERT INTO role (name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');
INSERT INTO users (user_name, email, password, role_id, cart_id)
VALUES ('Jan Kowalski', 'jan.kowalski@example.com', 'password123', 1,1),
       ('Maria Nowak', 'maria.nowak@example.com', 'password456', 2,2);
