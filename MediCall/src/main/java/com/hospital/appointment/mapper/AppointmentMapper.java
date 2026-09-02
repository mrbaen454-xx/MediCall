package com.hospital.appointment.mapper;

import com.hospital.appointment.dto.AppointmentRequestDTO;
import com.hospital.appointment.dto.AppointmentResponseDTO;
import com.hospital.appointment.entity.Appointment;
import com.hospital.doctor.entity.Doctor;
import com.hospital.patient.entity.Patient;
import com.hospital.schedule.entity.DoctorSchedule;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    public Appointment toEntity(AppointmentRequestDTO dto, Patient patient, Doctor doctor, DoctorSchedule schedule, Integer queueNumber) {
        if (dto == null) {
            return null;
        }

        return Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .schedule(schedule)
                .appointmentDate(dto.getAppointmentDate())
                .appointmentTime(dto.getAppointmentTime())
                .queueNumber(queueNumber)
                .status("WAITING")
                .build();
    }

    public AppointmentResponseDTO toDto(Appointment entity) {
        if (entity == null) {
            return null;
        }

        return AppointmentResponseDTO.builder()
                .id(entity.getId())
                .patientId(entity.getPatient() != null ? entity.getPatient().getId() : null)
                .patientName(entity.getPatient() != null ? entity.getPatient().getName() : null)
                .medicalRecordNumber(entity.getPatient() != null ? entity.getPatient().getMedicalRecordNumber() : null)
                .doctorId(entity.getDoctor() != null ? entity.getDoctor().getId() : null)
                .doctorName(entity.getDoctor() != null ? entity.getDoctor().getName() : null)
                .departmentName(entity.getDoctor() != null && entity.getDoctor().getDepartment() != null ? entity.getDoctor().getDepartment().getName() : null)
                .scheduleId(entity.getSchedule() != null ? entity.getSchedule().getId() : null)
                .appointmentDate(entity.getAppointmentDate())
                .appointmentTime(entity.getAppointmentTime())
                .queueNumber(entity.getQueueNumber())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }
}
