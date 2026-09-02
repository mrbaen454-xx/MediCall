-- V4__create_doctor_table.sql

CREATE TABLE doctors (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    nik VARCHAR(16) NOT NULL UNIQUE,
    sip VARCHAR(50) NOT NULL UNIQUE,
    str VARCHAR(50) NOT NULL UNIQUE,
    specialization VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    address TEXT,
    status VARCHAR(50) DEFAULT 'ACTIVE',
    photo_url VARCHAR(255),
    
    user_id INT UNIQUE,
    department_id INT NOT NULL,
    
    -- Audit fields
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(50),
    updated_by VARCHAR(50),
    
    -- Soft delete
    is_deleted BOOLEAN DEFAULT FALSE NOT NULL,
    
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL,
    FOREIGN KEY (department_id) REFERENCES departments(id) ON DELETE RESTRICT
);
