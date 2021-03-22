insert into roles (name) values
('ROLE_ADMIN'),
('ROLE_USER'),
('QA');

insert into users (username, password, role_id) values
('admin', '$2y$12$GT50OEtgLwabgzJBtj3w9OpGmUFe8C7/S0Aqhn4dcWfhWQKU1e5Ye', 1),
('user', '$2a$10$QI1ouaNFR6Auw9RKWvFiDON/4S0q9NTFiIgNXESRTQgkynHUtcC1e', 2),
('qa', '$2a$10$GIo37V5gMWzc7rBK8MU7tul9NEeKrZ2DvMk4ZXIkbx9DrtYdkRpAW', 3);

insert into role_permission (role_id, permissions) values
(1,'LIST_USERS'),
(2,'LIST_USERS'),
(3,'LIST_USERS'),
(1,'CREATE_USERS'),
(3,'CREATE_USERS'),
(1,'DELETE_USERS'),
(1,'EDIT_USERS');