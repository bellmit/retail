CREATE TABLE users
(
    id                        BIGINT       NOT NULL,
    created_on                datetime NULL,
    updated_on                datetime NULL,
    is_deleted                BIT(1) NULL,
    email                     VARCHAR(150) NOT NULL,
    email_verification_token  VARCHAR(255) NULL,
    email_verification_status BIT(1)       NOT NULL,
    encrypted_password        VARCHAR(255) NOT NULL,
    first_name                VARCHAR(50)  NOT NULL,
    last_name                 VARCHAR(50)  NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);