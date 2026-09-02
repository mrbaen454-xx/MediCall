package com.hospital.department.service;

import com.hospital.department.dto.DepartmentRequestDTO;
import com.hospital.department.dto.DepartmentResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DepartmentService {
    Page<DepartmentResponseDTO> getAllDepartments(String search, Pageable pageable);
    DepartmentResponseDTO getDepartmentById(Integer id);
    DepartmentResponseDTO createDepartment(DepartmentRequestDTO requestDTO);
    DepartmentResponseDTO updateDepartment(Integer id, DepartmentRequestDTO requestDTO);
    void deleteDepartment(Integer id);
}
