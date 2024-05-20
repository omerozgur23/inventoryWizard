insert into roles (id, role) 
values 
	(unhex(replace(uuid(), '-', '')), 'Admin'),
	(unhex(replace(uuid(), '-', '')), 'Warehouse Supervisor'),
	(unhex(replace(uuid(), '-', '')), 'Reporter')

