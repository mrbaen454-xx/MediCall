package com.hospital.patient.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientRequestDTO {

    @NotBlank(message = "Nama tidak boleh kosong")
    private String name;

    @NotBlank(message = "NIK tidak boleh kosong")
    @Size(min = 16, max = 16, message = "NIK harus 16 digit")
    private String nik;

    @NotNull(message = "Tanggal Lahir tidak boleh kosong")
    private LocalDate birthDate;

    @NotBlank(message = "Jenis Kelamin tidak boleh kosong")
    private String gender;

    private String address;
    private String phone;
    private String bloodGroup;
    private String bpjsNumber;
    private String status;
    private String photoUrl;
}
