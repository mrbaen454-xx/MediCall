-- V1__init.sql
-- Kosong untuk saat ini, tapi file ini harus ada agar Flyway berhasil jalan
-- pada saat aplikasi pertama kali dinyalakan.

CREATE TABLE IF NOT EXISTS dummy_init_table (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
