-- V9__create_prescription_tables.sql

CREATE TABLE prescriptions (
    id SERIAL PRIMARY KEY,
    medical_record_id INT NOT NULL UNIQUE,
    prescription_number VARCHAR(50) NOT NULL UNIQUE,
    status VARCHAR(50) DEFAULT 'PENDING',
    
    -- Audit fields
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(50),
    updated_by VARCHAR(50),
    
    -- Soft delete
    is_deleted BOOLEAN DEFAULT FALSE NOT NULL,
    
    FOREIGN KEY (medical_record_id) REFERENCES medical_records(id) ON DELETE RESTRICT
);

CREATE TABLE prescription_items (
    id SERIAL PRIMARY KEY,
    prescription_id INT NOT NULL,
    drug_name VARCHAR(255) NOT NULL,
    dosage VARCHAR(100) NOT NULL,
    notes TEXT,
    
    FOREIGN KEY (prescription_id) REFERENCES prescriptions(id) ON DELETE CASCADE
);
