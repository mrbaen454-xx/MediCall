-- V8__create_medical_record_table.sql

CREATE TABLE medical_records (
    id SERIAL PRIMARY KEY,
    appointment_id INT NOT NULL UNIQUE,
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
    
    complaint TEXT NOT NULL,
    diagnosis TEXT NOT NULL,
    
    blood_pressure VARCHAR(20),
    temperature DECIMAL(5, 2),
    weight DECIMAL(5, 2),
    height DECIMAL(5, 2),
    
    notes TEXT,
    status VARCHAR(50) DEFAULT 'COMPLETED',
    
    -- Audit fields
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(50),
    updated_by VARCHAR(50),
    
    -- Soft delete
    is_deleted BOOLEAN DEFAULT FALSE NOT NULL,
    
    FOREIGN KEY (appointment_id) REFERENCES appointments(id) ON DELETE RESTRICT,
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE RESTRICT,
    FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE RESTRICT
);
