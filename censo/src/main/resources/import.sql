INSERT INTO usuarios(username, password, enabled) VALUES ('user', '$2a$10$7S.UTZgY.o8F/ZpzuPQFU.z1P/wyeV/qVoTc0Tqv4R7PVztSaja.2', true)
INSERT INTO usuarios(username, password, enabled) VALUES ('admin', '$2a$10$uhZV0W8YkTZBZQCYnDW7.u9RsohRE5Wu8u9NviaMj4JMCwrKhH9VK', true)

INSERT INTO roles(authority) VALUES ('ROLE_VISUALIZAR');
INSERT INTO roles(authority) VALUES ('ROLE_ADMIN');
INSERT INTO roles(authority) VALUES ('ROLE_PRUEBA');

INSERT INTO usuario_rol(usuario_id, rol_id) VALUES (1, 1);
INSERT INTO usuario_rol(usuario_id, rol_id) VALUES (2, 1);
INSERT INTO usuario_rol(usuario_id, rol_id) VALUES (2, 2);