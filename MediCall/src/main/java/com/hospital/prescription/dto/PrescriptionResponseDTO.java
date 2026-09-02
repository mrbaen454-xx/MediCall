package com.hospital.prescription.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PrescriptionResponseDTO {
    private Integer id;
    private Integer medicalRecordId;
    
    private String prescriptionNumber;
    private String status;
    
    private Integer patientId;
    private String patientName;
    private String medicalRecordNumber;
    
    private Integer doctorId;
    private String doctorName;

    private List<PrescriptionItemResponseDTO> items;

    // Audit fields
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
