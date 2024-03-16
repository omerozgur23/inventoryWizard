insert into products values (unhex(replace(uuid(), '-', '')), 'Kolye', (select id from categories where category_name = 'Giyim'),(select id from suppliers where company_name = 'Tobeto'), '10', '200', '1000');
insert into products values (unhex(replace(uuid(), '-', '')), 'Gözlük', (select id from categories where category_name = 'Aksesuar'),(select id from suppliers where company_name = 'Tobeto'), '19', '100', '550');


