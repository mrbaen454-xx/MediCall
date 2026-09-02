package com.hospital.medicalrecord.service;

import com.hospital.medicalrecord.dto.MedicalRecordRequestDTO;
import com.hospital.medicalrecord.dto.MedicalRecordResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MedicalRecordService {
    Page<MedicalRecordResponseDTO> getAllMedicalRecords(String search, Pageable pageable);
    MedicalRecordResponseDTO getMedicalRecordById(Integer id);
    MedicalRecordResponseDTO createMedicalRecord(MedicalRecordRequestDTO requestDTO);
    MedicalRecordResponseDTO updateMedicalRecord(Integer id, MedicalRecordRequestDTO requestDTO);
    void deleteMedicalRecord(Integer id);
}
