-- Table: users
CREATE TABLE users (
    id UUID PRIMARY KEY,
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

-- Table: roles
CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    role_name VARCHAR NOT NULL UNIQUE,
    description TEXT
);

-- Join table: user_roles
CREATE TABLE user_roles (
    user_id UUID NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    role_id BIGINT NOT NULL REFERENCES roles (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);


INSERT INTO roles (id, role_name, description) VALUES
(1,'ROLE_ADMIN', 'Administrator with full access'),
(2,'ROLE_USER', 'Standard user with limited access'),
(3,'ROLE_COOK', 'User responsible for cooking-related tasks');
