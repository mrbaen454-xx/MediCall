package com.hospital.doctor.mapper;

import com.hospital.auth.entity.User;
import com.hospital.department.entity.Department;
import com.hospital.doctor.dto.DoctorRequestDTO;
import com.hospital.doctor.dto.DoctorResponseDTO;
import com.hospital.doctor.entity.Doctor;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {

    public Doctor toEntity(DoctorRequestDTO dto, Department department, User user) {
        if (dto == null) {
            return null;
        }

        return Doctor.builder()
                .name(dto.getName())
                .nik(dto.getNik())
                .sip(dto.getSip())
                .str(dto.getStr())
                .specialization(dto.getSpecialization())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .status(dto.getStatus() != null ? dto.getStatus() : "ACTIVE")
                .photoUrl(dto.getPhotoUrl())
                .department(department)
                .user(user)
                .build();
    }

    public void updateEntity(Doctor doctor, DoctorRequestDTO dto, Department department, User user) {
        if (dto == null) return;

        doctor.setName(dto.getName());
        doctor.setNik(dto.getNik());
        doctor.setSip(dto.getSip());
        doctor.setStr(dto.getStr());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setEmail(dto.getEmail());
        doctor.setPhone(dto.getPhone());
        doctor.setAddress(dto.getAddress());
        if (dto.getStatus() != null) {
            doctor.setStatus(dto.getStatus());
        }
        doctor.setPhotoUrl(dto.getPhotoUrl());

        if (department != null) {
            doctor.setDepartment(department);
        }
        if (user != null) {
            doctor.setUser(user);
        }
    }

    public DoctorResponseDTO toDto(Doctor entity) {
        if (entity == null) {
            return null;
        }

        return DoctorResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .nik(entity.getNik())
                .sip(entity.getSip())
                .str(entity.getStr())
                .specialization(entity.getSpecialization())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .address(entity.getAddress())
                .status(entity.getStatus())
                .photoUrl(entity.getPhotoUrl())
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .username(entity.getUser() != null ? entity.getUser().getUsername() : null)
                .departmentId(entity.getDepartment() != null ? entity.getDepartment().getId() : null)
                .departmentName(entity.getDepartment() != null ? entity.getDepartment().getName() : null)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }
}
