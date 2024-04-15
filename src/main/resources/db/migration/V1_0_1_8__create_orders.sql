CREATE TABLE orders (
  `id` BINARY(16) NOT NULL,
  `customer_id` BINARY(16) NOT NULL,
  `employee_id` BINARY(16) NOT NULL,
  `order_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
 /* UNIQUE INDEX `customer_id_UNIQUE` (`customer_id` ASC) VISIBLE,
  UNIQUE INDEX `employee_id_UNIQUE` (`employee_id` ASC) VISIBLE, */
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