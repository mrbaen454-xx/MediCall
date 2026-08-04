package com.example.reservasi.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_reservasi")
public class ReservasiEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String namaPasien;
    private String emailPasien;
    private Long idDoktor;
    private Long idJadwal;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNamaPasien() {
        return namaPasien;
    }
    public void setNamaPasien(String namaPasien) {
        this.namaPasien = namaPasien;
    }
    public String getEmailPasien() {
        return emailPasien;
    }
    public void setEmailPasien(String emailPasien) {
        this.emailPasien = emailPasien;
    }
    public Long getIdDoktor() {
        return idDoktor;
    }
    public void setIdDoktor(Long idDoktor) {
        this.idDoktor = idDoktor;
    }
    public Long getIdJadwal() {
        return idJadwal;
    }
    public void setIdJadwal(Long idJadwal) {
        this.idJadwal = idJadwal;
    }

}
