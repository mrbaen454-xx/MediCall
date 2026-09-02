-- V2__create_auth_tables.sql

CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_roles (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

CREATE TABLE refresh_tokens (
    id SERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL UNIQUE,
    user_id INT NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Insert Default Roles
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_DOCTOR');
INSERT INTO roles (name) VALUES ('ROLE_NURSE');
INSERT INTO roles (name) VALUES ('ROLE_PATIENT');
INSERT INTO roles (name) VALUES ('ROLE_PHARMACIST');
INSERT INTO roles (name) VALUES ('ROLE_LABORATORY');
INSERT INTO roles (name) VALUES ('ROLE_CASHIER');

-- Insert Default Admin User (Password is 'admin123' hashed with BCrypt)
INSERT INTO users (username, password, name) 
VALUES ('admin', '$2a$10$wN./FpE/K3hD5O6f8Y2a0e4s8T.UqN1M1k5H/XWvL1x.JvQ5lC9yW', 'Super Admin');

-- Assign ROLE_ADMIN to default admin
INSERT INTO user_roles (user_id, role_id) 
SELECT u.id, r.id FROM users u, roles r 
WHERE u.username = 'admin' AND r.name = 'ROLE_ADMIN';
