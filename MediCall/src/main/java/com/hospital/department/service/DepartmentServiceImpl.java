package com.hospital.department.service;

import com.hospital.department.dto.DepartmentRequestDTO;
import com.hospital.department.dto.DepartmentResponseDTO;
import com.hospital.department.entity.Department;
import com.hospital.department.mapper.DepartmentMapper;
import com.hospital.department.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<DepartmentResponseDTO> getAllDepartments(String search, Pageable pageable) {
        Specification<Department> spec = Specification.where(null);
        
        if (search != null && !search.trim().isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> 
                criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + search.toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + search.toLowerCase() + "%")
                )
            );
        }

        Page<Department> departments = departmentRepository.findAll(spec, pageable);
        return departments.map(departmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentResponseDTO getDepartmentById(Integer id) {
        Department department = findDepartmentByIdOrThrow(id);
        return departmentMapper.toDto(department);
    }

    @Override
    @Transactional
    public DepartmentResponseDTO createDepartment(DepartmentRequestDTO requestDTO) {
        if (departmentRepository.existsByName(requestDTO.getName())) {
            throw new RuntimeException("Department dengan nama '" + requestDTO.getName() + "' sudah ada");
        }
        
        Department department = departmentMapper.toEntity(requestDTO);
        department = departmentRepository.save(department);
        
        return departmentMapper.toDto(department);
    }

    @Override
    @Transactional
    public DepartmentResponseDTO updateDepartment(Integer id, DepartmentRequestDTO requestDTO) {
        Department department = findDepartmentByIdOrThrow(id);
        
        // Cek nama duplicate jika nama diubah
        if (!department.getName().equals(requestDTO.getName()) && departmentRepository.existsByName(requestDTO.getName())) {
            throw new RuntimeException("Department dengan nama '" + requestDTO.getName() + "' sudah ada");
        }
        
        departmentMapper.updateEntity(department, requestDTO);
        department = departmentRepository.save(department);
        
        return departmentMapper.toDto(department);
    }

    @Override
    @Transactional
    public void deleteDepartment(Integer id) {
        Department department = findDepartmentByIdOrThrow(id);
        departmentRepository.delete(department);
    }

    private Department findDepartmentByIdOrThrow(Integer id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department dengan ID " + id + " tidak ditemukan"));
    }
}
