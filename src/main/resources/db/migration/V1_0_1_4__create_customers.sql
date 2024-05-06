CREATE TABLE customers (
  `id` BINARY(16) NOT NULL,
  `company_name` VARCHAR(45) NOT NULL,
  `contact_name` VARCHAR(45) NOT NULL,
  `contact_phone` VARCHAR(45) NOT NULL,
  `contact_email` VARCHAR(45) NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  `tax_number` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX company_name_unq (company_name ASC) VISIBLE,
  UNIQUE INDEX tax_number_unq (tax_number ASC) VISIBLE
  );