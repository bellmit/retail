CREATE TABLE roles
(
    id         BIGINT NOT NULL AUTO_INCREMENT,
    created_on datetime NULL,
    updated_on datetime NULL,
    is_deleted BIT(1) NULL,
    name       VARCHAR(255) NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

ALTER TABLE roles
    ADD CONSTRAINT uc_roles_name UNIQUE (name);

ALTER TABLE users
    ADD role_id BIGINT NULL;

ALTER TABLE users
    MODIFY role_id BIGINT NOT NULL;

ALTER TABLE users
    ADD CONSTRAINT FK_USERS_ON_ROLE FOREIGN KEY (role_id) REFERENCES roles (id);