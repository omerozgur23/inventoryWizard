CREATE TABLE shelf (
  id BINARY(16) NOT NULL,
  count INT NOT NULL,
  capacity INT NOT NULL,
  product_id BINARY(16) NULL,
  status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE' NOT NULL,
  created_date DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
  inactive_date DATETIME DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT she_prod_prod_fk
    FOREIGN KEY (product_id)
    REFERENCES products (id)
); 