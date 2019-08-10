INSERT INTO usuarios(username, password, enabled) VALUES ('user', '$2a$10$7S.UTZgY.o8F/ZpzuPQFU.z1P/wyeV/qVoTc0Tqv4R7PVztSaja.2', true)
INSERT INTO usuarios(username, password, enabled) VALUES ('admin', '$2a$10$uhZV0W8YkTZBZQCYnDW7.u9RsohRE5Wu8u9NviaMj4JMCwrKhH9VK', true)

INSERT INTO roles(user_id, authority) VALUES (1, 'ROLE_VISUALIZAR');
INSERT INTO roles(user_id, authority) VALUES (2, 'ROLE_VISUALIZAR');
INSERT INTO roles(user_id, authority) VALUES (2, 'ROLE_ADMIN');