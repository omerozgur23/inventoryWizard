insert into user_role values (
(select id from users where email = 'omer@gmail.com'),
(select id from roles where role = 'Admin'));

insert into user_role values (
(select id from users where email = 'alpcan@gmail.com'),
(select id from roles where role = 'Warehouse Supervisor'));

insert into user_role values (
(select id from users where email = 'sevval@gmail.com'),
(select id from roles where role = 'Admin'));

insert into user_role values (
(select id from users where email = 'tugce@gmail.com'),
(select id from roles where role = 'Warehouse Supervisor'));

insert into user_role values (
(select id from users where email = 'sedat@gmail.com'),
(select id from roles where role = 'Admin'));

insert into user_role values (
(select id from users where email = 'bengi@gmail.com'),
(select id from roles where role = 'Reporter'));

insert into user_role values (
(select id from users where email = 'vildan@gmail.com'),
(select id from roles where role = 'Warehouse Supervisor'));