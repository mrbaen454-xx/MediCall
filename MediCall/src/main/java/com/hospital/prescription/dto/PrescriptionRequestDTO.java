package com.hospital.prescription.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PrescriptionRequestDTO {

    @NotNull(message = "Medical Record ID tidak boleh kosong")
    private Integer medicalRecordId;

    @NotEmpty(message = "Daftar obat tidak boleh kosong")
    @Valid
    private List<PrescriptionItemRequestDTO> items;
}
