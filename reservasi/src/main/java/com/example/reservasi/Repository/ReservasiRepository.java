package com.example.reservasi.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reservasi.Entity.ReservasiEntity;

public interface ReservasiRepository extends JpaRepository<ReservasiEntity, Long> {
    
}
