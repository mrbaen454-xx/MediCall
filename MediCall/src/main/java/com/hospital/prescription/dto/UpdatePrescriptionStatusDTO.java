package com.hospital.prescription.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdatePrescriptionStatusDTO {
    @NotBlank(message = "Status tidak boleh kosong")
    private String status;
}
