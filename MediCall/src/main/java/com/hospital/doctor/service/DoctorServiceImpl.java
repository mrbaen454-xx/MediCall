package com.hospital.doctor.service;

import com.hospital.auth.entity.User;
import com.hospital.auth.repository.UserRepository;
import com.hospital.department.entity.Department;
import com.hospital.department.repository.DepartmentRepository;
import com.hospital.doctor.dto.DoctorRequestDTO;
import com.hospital.doctor.dto.DoctorResponseDTO;
import com.hospital.doctor.entity.Doctor;
import com.hospital.doctor.mapper.DoctorMapper;
import com.hospital.doctor.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final DoctorMapper doctorMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<DoctorResponseDTO> getAllDoctors(String search, Pageable pageable) {
        Specification<Doctor> spec = Specification.where(null);
        
        if (search != null && !search.trim().isEmpty()) {
            String searchLower = "%" + search.toLowerCase() + "%";
            spec = spec.and((root, query, criteriaBuilder) -> 
                criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), searchLower),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("specialization")), searchLower),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("sip")), searchLower),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("str")), searchLower)
                )
            );
        }

        Page<Doctor> doctors = doctorRepository.findAll(spec, pageable);
        return doctors.map(doctorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public DoctorResponseDTO getDoctorById(Integer id) {
        Doctor doctor = findDoctorByIdOrThrow(id);
        return doctorMapper.toDto(doctor);
    }

    @Override
    @Transactional
    public DoctorResponseDTO createDoctor(DoctorRequestDTO requestDTO) {
        validateUniqueFields(requestDTO, null);

        Department department = findDepartmentOrThrow(requestDTO.getDepartmentId());
        User user = null;
        if (requestDTO.getUserId() != null) {
            user = findUserOrThrow(requestDTO.getUserId());
        }

        Doctor doctor = doctorMapper.toEntity(requestDTO, department, user);
        doctor = doctorRepository.save(doctor);
        
        return doctorMapper.toDto(doctor);
    }

    @Override
    @Transactional
    public DoctorResponseDTO updateDoctor(Integer id, DoctorRequestDTO requestDTO) {
        Doctor doctor = findDoctorByIdOrThrow(id);
        
        validateUniqueFields(requestDTO, doctor);

        Department department = null;
        if (requestDTO.getDepartmentId() != null && !requestDTO.getDepartmentId().equals(doctor.getDepartment().getId())) {
            department = findDepartmentOrThrow(requestDTO.getDepartmentId());
        }

        User user = doctor.getUser();
        if (requestDTO.getUserId() != null && (user == null || !requestDTO.getUserId().equals(user.getId()))) {
            user = findUserOrThrow(requestDTO.getUserId());
        }

        doctorMapper.updateEntity(doctor, requestDTO, department, user);
        doctor = doctorRepository.save(doctor);
        
        return doctorMapper.toDto(doctor);
    }

    @Override
    @Transactional
    public void deleteDoctor(Integer id) {
        Doctor doctor = findDoctorByIdOrThrow(id);
        doctorRepository.delete(doctor);
    }

    private Doctor findDoctorByIdOrThrow(Integer id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor dengan ID " + id + " tidak ditemukan"));
    }

    private Department findDepartmentOrThrow(Integer id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department dengan ID " + id + " tidak ditemukan"));
    }

    private User findUserOrThrow(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User dengan ID " + id + " tidak ditemukan"));
    }

    private void validateUniqueFields(DoctorRequestDTO dto, Doctor existingDoctor) {
        if (existingDoctor == null || !dto.getNik().equals(existingDoctor.getNik())) {
            if (doctorRepository.existsByNik(dto.getNik())) {
                throw new RuntimeException("NIK " + dto.getNik() + " sudah terdaftar");
            }
        }
        if (existingDoctor == null || !dto.getSip().equals(existingDoctor.getSip())) {
            if (doctorRepository.existsBySip(dto.getSip())) {
                throw new RuntimeException("SIP " + dto.getSip() + " sudah terdaftar");
            }
        }
        if (existingDoctor == null || !dto.getStr().equals(existingDoctor.getStr())) {
            if (doctorRepository.existsByStr(dto.getStr())) {
                throw new RuntimeException("STR " + dto.getStr() + " sudah terdaftar");
            }
        }
    }
}
