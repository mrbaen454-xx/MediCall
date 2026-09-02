package com.hospital.patient.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class PatientResponseDTO {
    private Integer id;
    private String medicalRecordNumber;
    private String name;
    private String nik;
    private LocalDate birthDate;
    private String gender;
    private String address;
    private String phone;
    private String bloodGroup;
    private String bpjsNumber;
    private String status;
    private String photoUrl;

    // Audit fields
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
