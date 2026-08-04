package com.example.doktor.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_doktor")
public class DoktorEntity {
    @Id
    private Long idDoktor;
    private String namaDoktor;
    private String spesialisasi;
    public Long getIdDoktor() {
        return idDoktor;
    }
    public void setIdDoktor(Long idDoktor) {
        this.idDoktor = idDoktor;
    }
    public String getNamaDoktor() {
        return namaDoktor;
    }
    public void setNamaDoktor(String namaDoktor) {
        this.namaDoktor = namaDoktor;
    }
    public String getSpesialisasi() {
        return spesialisasi;
    }
    public void setSpesialisasi(String spesialisasi) {
        this.spesialisasi = spesialisasi;
    }
 
}