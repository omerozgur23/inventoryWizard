CREATE TABLE categories (
  `id` BINARY(16) NOT NULL,
  `category_name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX category_name_unq (category_name ASC) VISIBLE);
