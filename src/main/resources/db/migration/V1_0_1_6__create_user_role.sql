CREATE TABLE user_role (
  user_id BINARY(16) NOT NULL,
  role_id BINARY(16) NOT NULL,
  PRIMARY KEY (user_id, role_id),
  INDEX us_role_role_fk_idx (role_id ASC) VISIBLE,
  CONSTRAINT us_role_us_fk
    FOREIGN KEY (user_id)
    REFERENCES users (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT us_role_role_fk
    FOREIGN KEY (role_id)
    REFERENCES roles (id));