package com.hospital.patient.service;

import com.hospital.patient.dto.PatientRequestDTO;
import com.hospital.patient.dto.PatientResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PatientService {
    Page<PatientResponseDTO> getAllPatients(String search, Pageable pageable);
    PatientResponseDTO getPatientById(Integer id);
    PatientResponseDTO createPatient(PatientRequestDTO requestDTO);
    PatientResponseDTO updatePatient(Integer id, PatientRequestDTO requestDTO);
    void deletePatient(Integer id);
}
