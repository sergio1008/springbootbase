INSERT INTO user (id, login, password) VALUES (1, 'ADMIN', '$2a$10$ddTKGJ2VKCUhiDnj/HkuIeE.1g0cNgzJ8o4Th92oyjOa6UstUrB.2');

INSERT INTO role (id, description, name) VALUES (1, 'Admin role', 'ADMIN');
INSERT INTO role (id, description, name) VALUES (2, 'User role', 'USER');
INSERT INTO role (id, description, name) VALUES (3, 'Operational role', 'OPERATIONAL');

INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (1, 2);


--usuario admin : ADMIN
-- contraseña admin: ADMIN_APP2020*