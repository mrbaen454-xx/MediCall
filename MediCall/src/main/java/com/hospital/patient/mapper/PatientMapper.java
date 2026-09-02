package com.hospital.patient.mapper;

import com.hospital.patient.dto.PatientRequestDTO;
import com.hospital.patient.dto.PatientResponseDTO;
import com.hospital.patient.entity.Patient;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

    public Patient toEntity(PatientRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        return Patient.builder()
                .name(dto.getName())
                .nik(dto.getNik())
                .birthDate(dto.getBirthDate())
                .gender(dto.getGender())
                .address(dto.getAddress())
                .phone(dto.getPhone())
                .bloodGroup(dto.getBloodGroup())
                .bpjsNumber(dto.getBpjsNumber())
                .status(dto.getStatus() != null ? dto.getStatus() : "ACTIVE")
                .photoUrl(dto.getPhotoUrl())
                // medicalRecordNumber will be generated in Service layer
                .build();
    }

    public void updateEntity(Patient patient, PatientRequestDTO dto) {
        if (dto == null) return;

        patient.setName(dto.getName());
        patient.setNik(dto.getNik());
        patient.setBirthDate(dto.getBirthDate());
        patient.setGender(dto.getGender());
        patient.setAddress(dto.getAddress());
        patient.setPhone(dto.getPhone());
        patient.setBloodGroup(dto.getBloodGroup());
        patient.setBpjsNumber(dto.getBpjsNumber());
        if (dto.getStatus() != null) {
            patient.setStatus(dto.getStatus());
        }
        patient.setPhotoUrl(dto.getPhotoUrl());
    }

    public PatientResponseDTO toDto(Patient entity) {
        if (entity == null) {
            return null;
        }

        return PatientResponseDTO.builder()
                .id(entity.getId())
                .medicalRecordNumber(entity.getMedicalRecordNumber())
                .name(entity.getName())
                .nik(entity.getNik())
                .birthDate(entity.getBirthDate())
                .gender(entity.getGender())
                .address(entity.getAddress())
                .phone(entity.getPhone())
                .bloodGroup(entity.getBloodGroup())
                .bpjsNumber(entity.getBpjsNumber())
                .status(entity.getStatus())
                .photoUrl(entity.getPhotoUrl())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }
}
