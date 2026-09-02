package com.hospital.medicalrecord.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class MedicalRecordResponseDTO {
    private Integer id;
    
    private Integer appointmentId;
    
    private Integer patientId;
    private String patientName;
    private String medicalRecordNumber;
    
    private Integer doctorId;
    private String doctorName;
    private String departmentName;
    
    private String complaint;
    private String diagnosis;
    private String bloodPressure;
    private BigDecimal temperature;
    private BigDecimal weight;
    private BigDecimal height;
    private String notes;
    private String status;

    // Audit fields
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
