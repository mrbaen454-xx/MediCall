package com.example.doktor.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.doktor.Entity.DoktorEntity;

public interface DoktorRepository extends JpaRepository<DoktorEntity, Long> {
    
}
