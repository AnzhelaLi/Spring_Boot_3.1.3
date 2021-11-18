drop table if exists users_roles;
drop table if exists users;
drop table if exists roles;

create table users (user_id bigint not null auto_increment, name varchar(30) not null,

                    surname varchar(30) not null, workplace varchar(30) not null, age int(10) not null,

                    salary int (100) not null, username varchar(40) not null,
                    password varchar(200) not null,
                    primary key(user_id), unique(username));


create table roles (role_id bigint not null auto_increment,
                    role varchar(30) not null,
                    primary key(role_id), unique(role));

create table users_roles (user_id BIGINT not null, role_id BIGINT not null,
                        primary key(user_id, role_id),

                        constraint FK_users foreign key (user_id) references users (user_id),

                        constraint FK_roles foreign key (role_id) references roles (role_id));
