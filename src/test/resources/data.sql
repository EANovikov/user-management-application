-- --USE user_management_db;
--
-- drop table if exists roles;
--
-- create table roles (
-- id serial not null AUTO_INCREMENT primary key,
-- name varchar(100) not null
-- -- foreign key (role_id) references roles (id) on update cascade on delete restrict
-- );
--
-- drop table if exists users;
--
-- create table users (
-- id serial not null AUTO_INCREMENT primary key,
-- username varchar(50) unique not null,
-- password varchar(100) not null,
-- role_id BIGINT UNSIGNED default null,
-- foreign key (role_id) references roles (id) on update cascade on delete cascade
-- );
--
-- drop table if exists permissions;
--
-- create table role_permission (
-- role_id BIGINT UNSIGNED not null,
-- permissions varchar(100) not null,
-- primary key (role_id, permissions),
-- foreign key (role_id) references roles (id) on update cascade on delete cascade
-- );

-- create table permissions (
-- id serial not null AUTO_INCREMENT primary key
--
-- )ENGINE=InnoDB;
-- drop table if exists role_permission;
--
-- create table role_permission (
-- role_id BIGINT UNSIGNED not null,
-- permission_id BIGINT UNSIGNED not null,
-- primary key (user_id, role_id),
-- foreign key (user_id) references users (id) on update cascade on delete restrict,
-- foreign key (role_id) references roles (id) on update cascade on delete restrict
-- )ENGINE=InnoDB;

--Insert the data

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