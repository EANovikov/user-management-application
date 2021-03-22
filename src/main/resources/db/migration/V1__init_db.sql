drop table if exists roles;

create table roles (
id serial not null AUTO_INCREMENT primary key,
name varchar(100) unique not null
)ENGINE=InnoDB;

drop table if exists users;

create table users (
id serial not null AUTO_INCREMENT primary key,
username varchar(50) unique not null,
password varchar(100) not null,
role_id BIGINT UNSIGNED default null,
foreign key (role_id) references roles (id) on update cascade on delete cascade
)ENGINE=InnoDB;

drop table if exists permissions;

create table role_permission (
role_id BIGINT UNSIGNED not null,
permissions varchar(100) not null,
primary key (role_id, permissions),
foreign key (role_id) references roles (id) on update cascade on delete cascade
)ENGINE=InnoDB;

insert into roles (name) values
('ROLE_ADMIN');

insert into users (username, password, role_id) values
('admin', '$2y$12$GT50OEtgLwabgzJBtj3w9OpGmUFe8C7/S0Aqhn4dcWfhWQKU1e5Ye', 1);

insert into role_permission (role_id, permissions) values
(1,'LIST_USERS'),
(1,'CREATE_USERS'),
(1,'DELETE_USERS'),
(1,'EDIT_USERS');