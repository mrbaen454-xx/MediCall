package com.hospital.medicalrecord.mapper;

import com.hospital.appointment.entity.Appointment;
import com.hospital.doctor.entity.Doctor;
import com.hospital.medicalrecord.dto.MedicalRecordRequestDTO;
import com.hospital.medicalrecord.dto.MedicalRecordResponseDTO;
import com.hospital.medicalrecord.entity.MedicalRecord;
import com.hospital.patient.entity.Patient;
import org.springframework.stereotype.Component;

@Component
public class MedicalRecordMapper {

    public MedicalRecord toEntity(MedicalRecordRequestDTO dto, Appointment appointment, Patient patient, Doctor doctor) {
        if (dto == null) {
            return null;
        }

        return MedicalRecord.builder()
                .appointment(appointment)
                .patient(patient)
                .doctor(doctor)
                .complaint(dto.getComplaint())
                .diagnosis(dto.getDiagnosis())
                .bloodPressure(dto.getBloodPressure())
                .temperature(dto.getTemperature())
                .weight(dto.getWeight())
                .height(dto.getHeight())
                .notes(dto.getNotes())
                .status(dto.getStatus() != null ? dto.getStatus() : "COMPLETED")
                .build();
    }

    public void updateEntity(MedicalRecord record, MedicalRecordRequestDTO dto) {
        if (dto == null) return;

        record.setComplaint(dto.getComplaint());
        record.setDiagnosis(dto.getDiagnosis());
        record.setBloodPressure(dto.getBloodPressure());
        record.setTemperature(dto.getTemperature());
        record.setWeight(dto.getWeight());
        record.setHeight(dto.getHeight());
        record.setNotes(dto.getNotes());
        if (dto.getStatus() != null) {
            record.setStatus(dto.getStatus());
        }
    }

    public MedicalRecordResponseDTO toDto(MedicalRecord entity) {
        if (entity == null) {
            return null;
        }

        return MedicalRecordResponseDTO.builder()
                .id(entity.getId())
                .appointmentId(entity.getAppointment() != null ? entity.getAppointment().getId() : null)
                .patientId(entity.getPatient() != null ? entity.getPatient().getId() : null)
                .patientName(entity.getPatient() != null ? entity.getPatient().getName() : null)
                .medicalRecordNumber(entity.getPatient() != null ? entity.getPatient().getMedicalRecordNumber() : null)
                .doctorId(entity.getDoctor() != null ? entity.getDoctor().getId() : null)
                .doctorName(entity.getDoctor() != null ? entity.getDoctor().getName() : null)
                .departmentName(entity.getDoctor() != null && entity.getDoctor().getDepartment() != null ? entity.getDoctor().getDepartment().getName() : null)
                .complaint(entity.getComplaint())
                .diagnosis(entity.getDiagnosis())
                .bloodPressure(entity.getBloodPressure())
                .temperature(entity.getTemperature())
                .weight(entity.getWeight())
                .height(entity.getHeight())
                .notes(entity.getNotes())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }
}
