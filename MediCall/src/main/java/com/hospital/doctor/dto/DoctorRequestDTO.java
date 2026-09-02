package com.hospital.doctor.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DoctorRequestDTO {

    @NotBlank(message = "Nama tidak boleh kosong")
    private String name;

    @NotBlank(message = "NIK tidak boleh kosong")
    @Size(min = 16, max = 16, message = "NIK harus 16 digit")
    private String nik;

    @NotBlank(message = "SIP tidak boleh kosong")
    private String sip;

    @NotBlank(message = "STR tidak boleh kosong")
    private String str;

    @NotBlank(message = "Spesialisasi tidak boleh kosong")
    private String specialization;

    @Email(message = "Format email tidak valid")
    private String email;

    private String phone;
    private String address;
    private String status;
    private String photoUrl;

    private Integer userId; // Optional, bisa null jika belum di-link ke user

    @NotNull(message = "Department ID tidak boleh kosong")
    private Integer departmentId;
}
