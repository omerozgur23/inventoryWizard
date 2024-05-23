CREATE TABLE invoice_items (
  id BINARY(16) NOT NULL,
  invoice_id BINARY(16) NOT NULL,
  product_id BINARY(16) NOT NULL,
  quantity INT NOT NULL,
  unit_price DECIMAL NOT NULL,
  total_amount DECIMAL NOT NULL,
  status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE' NOT NULL,
  created_date DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
  inactive_date DATETIME DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT invitem_inv_inv_fk
    FOREIGN KEY (invoice_id)
    REFERENCES invoices (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT invitem_prod_prod_fk
    FOREIGN KEY (product_id)
    REFERENCES products (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);