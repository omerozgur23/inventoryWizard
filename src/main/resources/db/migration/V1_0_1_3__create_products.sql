CREATE TABLE products (
  `id` BINARY(16) NOT NULL,
  `product_name` VARCHAR(45)NOT NULL,
  `category_id` BINARY(16) NOT NULL,
  supplier_id BINARY(16) NOT NULL,
  critical_count INT NOT NULL,
  `quantity` INT NULL,
  `purchase_price` DECIMAL NOT NULL,
  `unit_price` DECIMAL NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `prod_cate_cate_fk`
  	FOREIGN KEY (`category_id`)
  	REFERENCES categories (`id`)
  	ON DELETE NO ACTION
  	ON UPDATE NO ACTION,
  CONSTRAINT `prod_supp_supp_fk`
    FOREIGN KEY (`supplier_id`)
    REFERENCES suppliers (`id`)
);