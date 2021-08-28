insert into users (name, surname, workplace, age, salary, username, password)
values ('Ivan', 'Ivanov', 'fortuna', 50, 120000, 'ivi50', '$2a$10$wRtDsg/IbyEE2loh9Ek7eOIghm3QsxiHajbLuGP3kulYSfLu5rokK');
insert into users (name, surname, workplace, age, salary, username, password)
values ('John', 'Johnson', 'autopark', 33, 65000, 'JJ33', '$2a$10$Wg5ypZqTNQT3JBUTtKAIKOetaG1VTC.g2seHFVcN81CnVw8Dy/d7.');

insert into roles (role) values ('ROLE_ADMIN');
insert into roles (role) values ('ROLE_USER');

insert into users_roles (user_id, role_id) select user_id, role_id from users user,

                                                                      roles where user.username = 'JJ33' and roles.role = 'ROLE_USER';
insert into users_roles (user_id, role_id) select user_id, role_id from users user,

                                                                      roles where user.username = 'ivi50' and roles.role = 'ROLE_ADMIN';
insert into users_roles (user_id, role_id) select user_id, role_id from users user,

                                                                      roles where user.username = 'ivi50' and roles.role = 'ROLE_USER';