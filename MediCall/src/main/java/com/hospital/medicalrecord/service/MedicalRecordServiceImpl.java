package com.hospital.medicalrecord.service;

import com.hospital.appointment.entity.Appointment;
import com.hospital.appointment.repository.AppointmentRepository;
import com.hospital.medicalrecord.dto.MedicalRecordRequestDTO;
import com.hospital.medicalrecord.dto.MedicalRecordResponseDTO;
import com.hospital.medicalrecord.entity.MedicalRecord;
import com.hospital.medicalrecord.mapper.MedicalRecordMapper;
import com.hospital.medicalrecord.repository.MedicalRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final AppointmentRepository appointmentRepository;
    private final MedicalRecordMapper medicalRecordMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<MedicalRecordResponseDTO> getAllMedicalRecords(String search, Pageable pageable) {
        Specification<MedicalRecord> spec = Specification.where(null);
        
        if (search != null && !search.trim().isEmpty()) {
            String searchLower = "%" + search.toLowerCase() + "%";
            spec = spec.and((root, query, criteriaBuilder) -> 
                criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.join("patient").get("name")), searchLower),
                    criteriaBuilder.like(criteriaBuilder.lower(root.join("patient").get("medicalRecordNumber")), searchLower),
                    criteriaBuilder.like(criteriaBuilder.lower(root.join("doctor").get("name")), searchLower),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("diagnosis")), searchLower)
                )
            );
        }

        Page<MedicalRecord> records = medicalRecordRepository.findAll(spec, pageable);
        return records.map(medicalRecordMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public MedicalRecordResponseDTO getMedicalRecordById(Integer id) {
        MedicalRecord record = findMedicalRecordByIdOrThrow(id);
        return medicalRecordMapper.toDto(record);
    }

    @Override
    @Transactional
    public MedicalRecordResponseDTO createMedicalRecord(MedicalRecordRequestDTO requestDTO) {
        if (medicalRecordRepository.existsByAppointmentId(requestDTO.getAppointmentId())) {
            throw new RuntimeException("Rekam Medis untuk Appointment ID " + requestDTO.getAppointmentId() + " sudah pernah dibuat");
        }

        Appointment appointment = appointmentRepository.findById(requestDTO.getAppointmentId())
                .orElseThrow(() -> new RuntimeException("Appointment tidak ditemukan"));

        MedicalRecord record = medicalRecordMapper.toEntity(requestDTO, appointment, appointment.getPatient(), appointment.getDoctor());
        record = medicalRecordRepository.save(record);
        
        // Auto-update Appointment status to DONE
        appointment.setStatus("DONE");
        appointmentRepository.save(appointment);

        return medicalRecordMapper.toDto(record);
    }

    @Override
    @Transactional
    public MedicalRecordResponseDTO updateMedicalRecord(Integer id, MedicalRecordRequestDTO requestDTO) {
        MedicalRecord record = findMedicalRecordByIdOrThrow(id);
        
        if (!record.getAppointment().getId().equals(requestDTO.getAppointmentId())) {
            throw new RuntimeException("Appointment ID pada Rekam Medis tidak dapat diubah");
        }

        medicalRecordMapper.updateEntity(record, requestDTO);
        record = medicalRecordRepository.save(record);
        
        return medicalRecordMapper.toDto(record);
    }

    @Override
    @Transactional
    public void deleteMedicalRecord(Integer id) {
        MedicalRecord record = findMedicalRecordByIdOrThrow(id);
        medicalRecordRepository.delete(record);
    }

    private MedicalRecord findMedicalRecordByIdOrThrow(Integer id) {
        return medicalRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medical Record dengan ID " + id + " tidak ditemukan"));
    }
}
