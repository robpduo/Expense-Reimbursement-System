create table reimbursement_status (
	status_id int primary key generated always as identity,
	status varchar(64)
);

create table reimbursement_type (
	type_id int primary key generated always as identity,
	type varchar(64)
);

create table reimbursement (
	reimbursement_id int primary key generated always as identity,
	amount numeric not null,
	submitted_date date not null,
	resolved_date date,
	description varchar(128), 
	reimbursement_author int references users(user_id), 
	reimbursement_resolver int references users(user_id),
	reimbursement_status int references reimbursement_status(status_id), 
	reimbursement_type int references reimbursement_type (type_id)
);

drop table reimbursement;

create table users (
	user_id int primary key generated always as identity,
	username varchar(64),
	password varchar(25),
	first_name varchar(28),
	last_name varchar(28),
	email varchar(64),
	role int references user_roles(role_id)
);

create table user_roles ( 
	role_id int primary key,
	role varchar(28) unique
);

insert into user_roles (role_id, role) values (1, 'MANAGER');
insert into user_roles  (role_id, role) values (2, 'EMPLOYEE');

select * from user_roles;
select * from users;
select * from reimbursement;
select * from reimbursement_status;
select * from reimbursement_type;

alter table users 
modify column user_id primary key generated always as identity;

drop table users cascade;

UPDATE users SET password = 'password' WHERE user_id = 1;

insert into users (username, password, first_name, last_name, email, role) values ('kgoligly0', 'z1xhZ6IXPscq', 'Karolina', 'Goligly', 'kgoligly0@hibu.com', 2);
insert into users (username, password, first_name, last_name, email, role) values ('tmclarnon1', 'RLGqE9a', 'Tess', 'McLarnon', 'tmclarnon1@nhs.uk', 1);
insert into users (username, password, first_name, last_name, email, role) values ('bbeddie2', 'NsEZdQl0', 'Benedict', 'Beddie', 'bbeddie2@weather.com', 1);
insert into users (username, password, first_name, last_name, email, role) values ('dsuston3', '9xMPwuEXfsBK', 'Daryn', 'Suston', 'dsuston3@mapquest.com', 1);
insert into users (username, password, first_name, last_name, email, role) values ('jmaunder4', '6fsfze', 'Jorie', 'Maunder', 'jmaunder4@ed.gov', 1);
