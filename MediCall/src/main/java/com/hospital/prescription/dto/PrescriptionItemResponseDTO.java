package com.hospital.prescription.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PrescriptionItemResponseDTO {
    private Integer id;
    private String drugName;
    private String dosage;
    private String notes;
}
