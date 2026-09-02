-- V5__create_patient_table.sql

CREATE TABLE patients (
    id SERIAL PRIMARY KEY,
    medical_record_number VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    nik VARCHAR(16) NOT NULL UNIQUE,
    birth_date DATE NOT NULL,
    gender VARCHAR(10) NOT NULL,
    address TEXT,
    phone VARCHAR(20),
    blood_group VARCHAR(5),
    bpjs_number VARCHAR(20),
    status VARCHAR(50) DEFAULT 'ACTIVE',
    photo_url VARCHAR(255),
    
    -- Audit fields
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(50),
    updated_by VARCHAR(50),
    
    -- Soft delete
    is_deleted BOOLEAN DEFAULT FALSE NOT NULL
);
