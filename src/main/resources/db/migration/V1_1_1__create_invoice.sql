CREATE TABLE invoices (
  id BINARY(16) NOT NULL,
  customer_id BINARY(16) NOT NULL,
  order_id BINARY(16) NOT NULL,
  total_amount DECIMAL NOT NULL,
  status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE' NOT NULL,
  created_date DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
  inactive_date DATETIME DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT inv_cus_cus_fk
    FOREIGN KEY (customer_id)
    REFERENCES customers (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT inv_or_or_fk
    FOREIGN KEY (order_id)
    REFERENCES orders (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);