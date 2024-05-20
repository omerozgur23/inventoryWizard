insert into users (id, first_name, last_name, email, password) 
values 
	(unhex(replace(uuid(), '-', '')), 'Ömer', 'Özgür', 'omer@gmail.com', '216323'),
	(unhex(replace(uuid(), '-', '')), 'Alpcan', 'Yüksel', 'alpcan@gmail.com', 'alpcan'),
	(unhex(replace(uuid(), '-', '')), 'Şevval', 'Yılmaz', 'sevval@gmail.com', 'sevval'),
	(unhex(replace(uuid(), '-', '')), 'Tuğçe', 'Dalay', 'tugce@gmail.com', 'tugce'),
	(unhex(replace(uuid(), '-', '')), 'Bengisu', 'Karadeniz', 'bengi@gmail.com', 'bengi'),
	(unhex(replace(uuid(), '-', '')), 'Vildan', 'Karaduman', 'vildan@gmail.com', 'vildan')
