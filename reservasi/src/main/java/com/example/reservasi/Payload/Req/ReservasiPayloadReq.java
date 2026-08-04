package com.example.reservasi.Payload.Req;


public class ReservasiPayloadReq {
    private String namaPasien;
    private String emailPasien;
    private Long idDoktor;
    private Long idJadwal;
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
