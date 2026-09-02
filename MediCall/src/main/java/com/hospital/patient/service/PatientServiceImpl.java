package com.hospital.patient.service;

import com.hospital.patient.dto.PatientRequestDTO;
import com.hospital.patient.dto.PatientResponseDTO;
import com.hospital.patient.entity.Patient;
import com.hospital.patient.mapper.PatientMapper;
import com.hospital.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<PatientResponseDTO> getAllPatients(String search, Pageable pageable) {
        Specification<Patient> spec = Specification.where(null);
        
        if (search != null && !search.trim().isEmpty()) {
            String searchLower = "%" + search.toLowerCase() + "%";
            spec = spec.and((root, query, criteriaBuilder) -> 
                criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), searchLower),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("medicalRecordNumber")), searchLower),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("nik")), searchLower),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("phone")), searchLower)
                )
            );
        }

        Page<Patient> patients = patientRepository.findAll(spec, pageable);
        return patients.map(patientMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public PatientResponseDTO getPatientById(Integer id) {
        Patient patient = findPatientByIdOrThrow(id);
        return patientMapper.toDto(patient);
    }

    @Override
    @Transactional
    public PatientResponseDTO createPatient(PatientRequestDTO requestDTO) {
        if (patientRepository.existsByNik(requestDTO.getNik())) {
            throw new RuntimeException("NIK " + requestDTO.getNik() + " sudah terdaftar");
        }

        Patient patient = patientMapper.toEntity(requestDTO);
        
        // Generate Medical Record Number (RM-UUID for simplicity, can be customized)
        String generatedRM = "RM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        patient.setMedicalRecordNumber(generatedRM);

        patient = patientRepository.save(patient);
        
        return patientMapper.toDto(patient);
    }

    @Override
    @Transactional
    public PatientResponseDTO updatePatient(Integer id, PatientRequestDTO requestDTO) {
        Patient patient = findPatientByIdOrThrow(id);
        
        if (!patient.getNik().equals(requestDTO.getNik()) && patientRepository.existsByNik(requestDTO.getNik())) {
            throw new RuntimeException("NIK " + requestDTO.getNik() + " sudah terdaftar pada pasien lain");
        }

        patientMapper.updateEntity(patient, requestDTO);
        patient = patientRepository.save(patient);
        
        return patientMapper.toDto(patient);
    }

    @Override
    @Transactional
    public void deletePatient(Integer id) {
        Patient patient = findPatientByIdOrThrow(id);
        patientRepository.delete(patient);
    }

    private Patient findPatientByIdOrThrow(Integer id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient dengan ID " + id + " tidak ditemukan"));
    }
}
