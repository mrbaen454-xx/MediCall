package com.hospital.medicalrecord.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MedicalRecordRequestDTO {

    @NotNull(message = "Appointment ID tidak boleh kosong")
    private Integer appointmentId;

    @NotBlank(message = "Keluhan tidak boleh kosong")
    private String complaint;

    @NotBlank(message = "Diagnosa tidak boleh kosong")
    private String diagnosis;

    private String bloodPressure;
    private BigDecimal temperature;
    private BigDecimal weight;
    private BigDecimal height;
    private String notes;
    private String status;
}
