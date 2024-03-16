CREATE TABLE shelf (
  `id` BINARY(16) NOT NULL,
  `count` INT NOT NULL,
  `capacity` INT NOT NULL,
  category_id BINARY(16) NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT she_cat_cat_fk
    FOREIGN KEY (category_id)
    REFERENCES categories (id)
); 