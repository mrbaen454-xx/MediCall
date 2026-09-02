package com.hospital.prescription.service;

import com.hospital.medicalrecord.entity.MedicalRecord;
import com.hospital.medicalrecord.repository.MedicalRecordRepository;
import com.hospital.prescription.dto.PrescriptionRequestDTO;
import com.hospital.prescription.dto.PrescriptionResponseDTO;
import com.hospital.prescription.dto.UpdatePrescriptionStatusDTO;
import com.hospital.prescription.entity.Prescription;
import com.hospital.prescription.mapper.PrescriptionMapper;
import com.hospital.prescription.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final PrescriptionMapper prescriptionMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<PrescriptionResponseDTO> getAllPrescriptions(String search, Pageable pageable) {
        Specification<Prescription> spec = Specification.where(null);
        
        if (search != null && !search.trim().isEmpty()) {
            String searchLower = "%" + search.toLowerCase() + "%";
            spec = spec.and((root, query, criteriaBuilder) -> 
                criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("prescriptionNumber")), searchLower),
                    criteriaBuilder.like(criteriaBuilder.lower(root.join("medicalRecord").join("patient").get("name")), searchLower),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), searchLower)
                )
            );
        }

        Page<Prescription> prescriptions = prescriptionRepository.findAll(spec, pageable);
        return prescriptions.map(prescriptionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public PrescriptionResponseDTO getPrescriptionById(Integer id) {
        Prescription prescription = findPrescriptionByIdOrThrow(id);
        return prescriptionMapper.toDto(prescription);
    }

    @Override
    @Transactional
    public PrescriptionResponseDTO createPrescription(PrescriptionRequestDTO requestDTO) {
        if (prescriptionRepository.existsByMedicalRecordId(requestDTO.getMedicalRecordId())) {
            throw new RuntimeException("Resep obat untuk Medical Record ID " + requestDTO.getMedicalRecordId() + " sudah pernah dibuat");
        }

        MedicalRecord medicalRecord = medicalRecordRepository.findById(requestDTO.getMedicalRecordId())
                .orElseThrow(() -> new RuntimeException("Medical Record tidak ditemukan"));

        String prescriptionNumber = generatePrescriptionNumber();
        
        Prescription prescription = prescriptionMapper.toEntity(requestDTO, medicalRecord, prescriptionNumber);
        prescription = prescriptionRepository.save(prescription);
        
        return prescriptionMapper.toDto(prescription);
    }

    @Override
    @Transactional
    public PrescriptionResponseDTO updatePrescription(Integer id, PrescriptionRequestDTO requestDTO) {
        Prescription prescription = findPrescriptionByIdOrThrow(id);
        
        if (!prescription.getMedicalRecord().getId().equals(requestDTO.getMedicalRecordId())) {
            throw new RuntimeException("Medical Record ID pada Resep tidak dapat diubah");
        }

        if (!prescription.getStatus().equals("PENDING")) {
            throw new RuntimeException("Resep hanya dapat diubah jika statusnya PENDING");
        }

        prescriptionMapper.updateEntity(prescription, requestDTO);
        prescription = prescriptionRepository.save(prescription);
        
        return prescriptionMapper.toDto(prescription);
    }

    @Override
    @Transactional
    public PrescriptionResponseDTO updatePrescriptionStatus(Integer id, UpdatePrescriptionStatusDTO requestDTO) {
        Prescription prescription = findPrescriptionByIdOrThrow(id);
        prescription.setStatus(requestDTO.getStatus());
        prescription = prescriptionRepository.save(prescription);
        
        return prescriptionMapper.toDto(prescription);
    }

    @Override
    @Transactional
    public void deletePrescription(Integer id) {
        Prescription prescription = findPrescriptionByIdOrThrow(id);
        
        if (!prescription.getStatus().equals("PENDING")) {
            throw new RuntimeException("Hanya resep berstatus PENDING yang dapat dihapus");
        }
        
        prescriptionRepository.delete(prescription);
    }

    private Prescription findPrescriptionByIdOrThrow(Integer id) {
        return prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resep obat dengan ID " + id + " tidak ditemukan"));
    }

    private String generatePrescriptionNumber() {
        String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long count = prescriptionRepository.count() + 1; // Simplifikasi untuk demo, di production butuh sequence/atomic yang thread-safe per tanggal
        return String.format("RSP-%s-%04d", dateStr, count);
    }
}
