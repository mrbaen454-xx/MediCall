package com.hospital.doctor.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DoctorResponseDTO {
    private Integer id;
    private String name;
    private String nik;
    private String sip;
    private String str;
    private String specialization;
    private String email;
    private String phone;
    private String address;
    private String status;
    private String photoUrl;
    
    // Relasi info ringan
    private Integer userId;
    private String username;
    
    private Integer departmentId;
    private String departmentName;

    // Audit fields
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
