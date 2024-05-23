CREATE TABLE categories (
  id BINARY(16) NOT NULL,
  category_name VARCHAR(45) NOT NULL,
  status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE' NOT NULL,
  created_date DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
  inactive_date DATETIME DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX category_name_unq (category_name ASC) VISIBLE);
