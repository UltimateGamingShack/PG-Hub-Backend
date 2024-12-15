CREATE TABLE admin (
    admin_id SERIAL PRIMARY KEY,
    admin_name VARCHAR(255) NOT NULL,
    admin_email VARCHAR(255) UNIQUE NOT NULL,
    admin_password VARCHAR(255) NOT NULL,
    admin_phone_no VARCHAR(15) UNIQUE NOT NULL,
    pg_id INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    role VARCHAR(50) CHECK (role IN ('admin', 'user', 'cook')) NOT NULL
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
	user_name VARCHAR(255) NOT NULL,
    user_phone_no VARCHAR(15) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    pg_id INTEGER NOT NULL,
	admin_id INTEGER NOT NULL REFERENCES admin(admin_id),
    password VARCHAR(255) NOT NULL,
    user_image BYTEA,
    gender CHAR(1) CHECK (gender IN ('M', 'F', 'O')),
    room_no INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    role VARCHAR(50) CHECK (role IN ('admin', 'user', 'cook')) NOT NULL
);

CREATE TABLE cook (
    cook_id SERIAL PRIMARY KEY,
    pg_id INTEGER NOT NULL,
    name VARCHAR(255) NOT NULL,
    cook_phone_no VARCHAR(15) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	admin_id INTEGER NOT NULL REFERENCES admin(admin_id),
    role VARCHAR(50) CHECK (role IN ('admin', 'user', 'cook')) NOT NULL
);

INSERT INTO admin (admin_id, admin_name, admin_email, admin_password, admin_phone_no, pg_id, role) VALUES
(1, 'John Doe', 'john.doe@example.com', 'secureadmin1', '1234567890', 1, 'admin'),
(2, 'Jane Smith', 'jane.smith@example.com', 'secureadmin2', '0987654321', 2, 'admin');

INSERT INTO users (user_name, user_phone_no, email, pg_id, admin_id, password, user_image, gender, room_no, role) VALUES
('Alice Johnson', '1234567890', 'user1@example.com',  1, 1, 'password123', decode('89504E470D0A1A0A0000000D49484452000000100000001008020000009091682F', 'hex'), 'M', 101, 'user'),
('Bob Smith', '9876543210', 'user2@example.com',  2, 2, 'securepass', decode('89504E470D0A1A0A0000000D49484452000000100000001008020000009091682F', 'hex'), 'F', 102, 'user'),
('Charlie Brown', '5555555555', 'user3@example.com', 1, 1, 'mypassword', decode('89504E470D0A1A0A0000000D49484452000000100000001008020000009091682F', 'hex'), 'O', 103, 'user'),
('Diana Prince', '4444444444', 'user4@example.com', 3, 2, 'pass1234', decode('89504E470D0A1A0A0000000D49484452000000100000001008020000009091682F', 'hex'), 'M', 104, 'user');

INSERT INTO cook (pg_id, name, cook_phone_no, password, admin_id, role) VALUES
(1, 'Alice Brown', '1112223333', 'cookpass1', 1, 'cook'),
(2, 'Bob White', '4445556666', 'cookpass2', 2, 'cook'),
(3, 'Charlie Green', '7778889999', 'cookpass3', 1, 'cook');
