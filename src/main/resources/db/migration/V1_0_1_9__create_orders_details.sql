CREATE TABLE order_details (
  id BINARY(16) NOT NULL,
  order_id BINARY(16) NOT NULL,
  product_id BINARY(16) NOT NULL,
  quantity INT NOT NULL,
  unit_price DECIMAL NOT NULL,
  total_price DECIMAL NOT NULL,
  invoiced_quantity INT NOT NULL DEFAULT 0,
  status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE' NOT NULL,
  created_date DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
  inactive_date DATETIME DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `det_or_or_fk`
    FOREIGN KEY (`order_id`)
    REFERENCES `inventorywizard`.`orders` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `det_prod_prod_fk`
    FOREIGN KEY (`product_id`)
    REFERENCES `inventorywizard`.`products` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);