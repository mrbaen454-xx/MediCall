package com.example.doktor.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.doktor.Entity.JadwalEntity;

public interface JadwalRepository extends JpaRepository<JadwalEntity, Long> {
    List<JadwalEntity> findByIdDoktor(Long idDoktor);
    JadwalEntity findByIdJadwal(Long idJadwal);
}
