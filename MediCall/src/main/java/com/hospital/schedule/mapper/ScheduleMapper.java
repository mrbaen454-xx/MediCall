package com.hospital.schedule.mapper;

import com.hospital.doctor.entity.Doctor;
import com.hospital.schedule.dto.ScheduleRequestDTO;
import com.hospital.schedule.dto.ScheduleResponseDTO;
import com.hospital.schedule.entity.DoctorSchedule;
import org.springframework.stereotype.Component;

@Component
public class ScheduleMapper {

    public DoctorSchedule toEntity(ScheduleRequestDTO dto, Doctor doctor) {
        if (dto == null) {
            return null;
        }

        return DoctorSchedule.builder()
                .doctor(doctor)
                .dayOfWeek(dto.getDayOfWeek())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .quota(dto.getQuota())
                .status(dto.getStatus() != null ? dto.getStatus() : "ACTIVE")
                .build();
    }

    public void updateEntity(DoctorSchedule schedule, ScheduleRequestDTO dto, Doctor doctor) {
        if (dto == null) return;

        schedule.setDayOfWeek(dto.getDayOfWeek());
        schedule.setStartTime(dto.getStartTime());
        schedule.setEndTime(dto.getEndTime());
        schedule.setQuota(dto.getQuota());
        
        if (dto.getStatus() != null) {
            schedule.setStatus(dto.getStatus());
        }
        
        if (doctor != null) {
            schedule.setDoctor(doctor);
        }
    }

    public ScheduleResponseDTO toDto(DoctorSchedule entity) {
        if (entity == null) {
            return null;
        }

        return ScheduleResponseDTO.builder()
                .id(entity.getId())
                .doctorId(entity.getDoctor() != null ? entity.getDoctor().getId() : null)
                .doctorName(entity.getDoctor() != null ? entity.getDoctor().getName() : null)
                .departmentName(entity.getDoctor() != null && entity.getDoctor().getDepartment() != null ? entity.getDoctor().getDepartment().getName() : null)
                .dayOfWeek(entity.getDayOfWeek())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .quota(entity.getQuota())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }
}
