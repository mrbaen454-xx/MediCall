package com.hospital.appointment.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Data
@Builder
public class AppointmentResponseDTO {
    private Integer id;
    
    private Integer patientId;
    private String patientName;
    private String medicalRecordNumber;
    
    private Integer doctorId;
    private String doctorName;
    private String departmentName;
    
    private Integer scheduleId;
    
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    
    private Integer queueNumber;
    private String status;

    // Audit fields
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
