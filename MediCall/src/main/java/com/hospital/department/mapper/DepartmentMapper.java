package com.hospital.department.mapper;

import com.hospital.department.dto.DepartmentRequestDTO;
import com.hospital.department.dto.DepartmentResponseDTO;
import com.hospital.department.entity.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

    public Department toEntity(DepartmentRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Department department = new Department();
        department.setName(dto.getName());
        department.setDescription(dto.getDescription());
        return department;
    }

    public void updateEntity(Department department, DepartmentRequestDTO dto) {
        if (dto == null) {
            return;
        }
        
        if (dto.getName() != null) {
            department.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            department.setDescription(dto.getDescription());
        }
    }

    public DepartmentResponseDTO toDto(Department entity) {
        if (entity == null) {
            return null;
        }
        
        return DepartmentResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }
}
