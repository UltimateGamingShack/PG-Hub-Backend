-- Table: users
CREATE TABLE users (
    id UUID PRIMARY KEY,
    user_name VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL,
    email VARCHAR NOT NULL UNIQUE,
    phone_no VARCHAR NOT NULL UNIQUE,
    pg_id INTEGER NOT NULL,
    user_image VARCHAR,
    gender CHAR(1) NOT NULL,
    room_no INTEGER,
    email_verified BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_gender CHECK (gender IN ('M', 'F', 'O'))
);

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_phone_no ON users(phone_no);
CREATE INDEX idx_users_pg_id ON users(pg_id);

-- Table: roles
CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    role_name VARCHAR NOT NULL UNIQUE,
    description TEXT
);

CREATE INDEX idx_roles_role_name ON roles(role_name);

-- Join table: user_roles
CREATE TABLE user_roles (
    user_id UUID NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    role_id BIGINT NOT NULL REFERENCES roles (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);

CREATE INDEX idx_user_roles_user_id ON user_roles(user_id);
CREATE INDEX idx_user_roles_role_id ON user_roles(role_id);

-- Insert roles
INSERT INTO roles (id, role_name, description) VALUES
(1,'ROLE_ADMIN', 'Administrator with full access'),
(2,'ROLE_USER', 'Standard user with limited access'),
(3,'ROLE_COOK', 'User responsible for cooking-related tasks');


CREATE TABLE refresh_tokens
(
    id         UUID PRIMARY KEY,
    user_id    UUID NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    expires_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE INDEX idx_refresh_token_user_id ON refresh_tokens (user_id);