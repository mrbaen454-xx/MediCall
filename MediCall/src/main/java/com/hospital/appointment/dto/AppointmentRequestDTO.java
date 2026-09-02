package com.hospital.appointment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentRequestDTO {

    @NotNull(message = "Patient ID tidak boleh kosong")
    private Integer patientId;

    @NotNull(message = "Doctor ID tidak boleh kosong")
    private Integer doctorId;

    @NotNull(message = "Tanggal Appointment tidak boleh kosong")
    private LocalDate appointmentDate;

    @NotNull(message = "Jam Appointment tidak boleh kosong")
    private LocalTime appointmentTime;
}
