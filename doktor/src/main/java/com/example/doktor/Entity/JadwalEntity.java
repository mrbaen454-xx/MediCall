package com.example.doktor.Entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_jadwal")
public class JadwalEntity {
    @Id
    private Long idJadwal;
    private Long idDoktor;
    private LocalDate tanggal;
    private LocalTime jam;
    private String status;
    public Long getIdJadwal() {
        return idJadwal;
    }
    public void setIdJadwal(Long idJadwal) {
        this.idJadwal = idJadwal;
    }
    public Long getIdDoktor() {
        return idDoktor;
    }
    public void setIdDoktor(Long idDoktor) {
        this.idDoktor = idDoktor;
    }
    public LocalDate getTanggal() {
        return tanggal;
    }
    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }
    public LocalTime getJam() {
        return jam;
    }
    public void setJam(LocalTime jam) {
        this.jam = jam;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
}
