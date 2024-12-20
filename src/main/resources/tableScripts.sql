-- Table: users
drop table if exists users;
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    user_name VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    email VARCHAR NOT NULL UNIQUE,
    phone_no VARCHAR NOT NULL UNIQUE,
    pg_id INTEGER NOT NULL,
    user_image BYTEA,
    gender CHAR(1) NOT NULL,
    room_no INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_gender CHECK (gender IN ('M', 'F', 'O'))
);

drop table if exists roles;
-- Table: roles
CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    role_name VARCHAR NOT NULL UNIQUE,
    description TEXT
);

drop table if exists user_roles;
-- Join table: user_roles
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    role_id BIGINT NOT NULL REFERENCES roles (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);


INSERT INTO roles (role_name, description) VALUES
('ROLE_ADMIN', 'Administrator with full access'),
('ROLE_USER', 'Standard user with limited access'),
('ROLE_COOK', 'User responsible for cooking-related tasks');
