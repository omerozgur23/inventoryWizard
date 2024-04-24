CREATE TABLE orders (
  `id` BINARY(16) NOT NULL,
  `customer_id` BINARY(16) NOT NULL,
  `employee_id` BINARY(16) NOT NULL,
  `order_date` DATETIME NOT NULL,
  order_price DECIMAL NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `or_cus_cus_fk`
    FOREIGN KEY (`customer_id`)
    REFERENCES `inventorywizard`.`customers` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `or_emp_emp_fk`
    FOREIGN KEY (`employee_id`)
    REFERENCES `inventorywizard`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);