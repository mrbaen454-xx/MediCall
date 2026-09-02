package com.hospital.appointment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateAppointmentStatusDTO {
    @NotBlank(message = "Status tidak boleh kosong")
    private String status;
}
