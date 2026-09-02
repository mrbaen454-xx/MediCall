package com.hospital.prescription.service;

import com.hospital.prescription.dto.PrescriptionRequestDTO;
import com.hospital.prescription.dto.PrescriptionResponseDTO;
import com.hospital.prescription.dto.UpdatePrescriptionStatusDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PrescriptionService {
    Page<PrescriptionResponseDTO> getAllPrescriptions(String search, Pageable pageable);
    PrescriptionResponseDTO getPrescriptionById(Integer id);
    PrescriptionResponseDTO createPrescription(PrescriptionRequestDTO requestDTO);
    PrescriptionResponseDTO updatePrescription(Integer id, PrescriptionRequestDTO requestDTO);
    PrescriptionResponseDTO updatePrescriptionStatus(Integer id, UpdatePrescriptionStatusDTO requestDTO);
    void deletePrescription(Integer id);
}
