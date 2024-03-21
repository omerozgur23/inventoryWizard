CREATE TABLE users (
  id BINARY(16) NOT NULL,
  first_name VARCHAR(20) NOT NULL,
  last_name VARCHAR(20) NOT NULL,
  email VARCHAR(45) NULL,
  password VARCHAR(200) NULL,
  PRIMARY KEY (id));
  