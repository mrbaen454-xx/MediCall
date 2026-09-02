package com.hospital.schedule.dto;

import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Data
@Builder
public class ScheduleResponseDTO {
    private Integer id;
    private Integer doctorId;
    private String doctorName;
    private String departmentName;
    
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer quota;
    private String status;

    // Audit fields
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
