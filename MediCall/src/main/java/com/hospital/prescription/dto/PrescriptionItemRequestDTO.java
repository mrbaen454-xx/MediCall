package com.hospital.prescription.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PrescriptionItemRequestDTO {

    @NotBlank(message = "Nama obat tidak boleh kosong")
    private String drugName;

    @NotBlank(message = "Dosis (dosage) tidak boleh kosong")
    private String dosage;

    private String notes;
}
