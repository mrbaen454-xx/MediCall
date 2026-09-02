package com.hospital.prescription.mapper;

import com.hospital.medicalrecord.entity.MedicalRecord;
import com.hospital.prescription.dto.PrescriptionItemRequestDTO;
import com.hospital.prescription.dto.PrescriptionItemResponseDTO;
import com.hospital.prescription.dto.PrescriptionRequestDTO;
import com.hospital.prescription.dto.PrescriptionResponseDTO;
import com.hospital.prescription.entity.Prescription;
import com.hospital.prescription.entity.PrescriptionItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PrescriptionMapper {

    public Prescription toEntity(PrescriptionRequestDTO dto, MedicalRecord medicalRecord, String prescriptionNumber) {
        if (dto == null) {
            return null;
        }

        Prescription prescription = Prescription.builder()
                .medicalRecord(medicalRecord)
                .prescriptionNumber(prescriptionNumber)
                .status("PENDING")
                .build();

        if (dto.getItems() != null) {
            for (PrescriptionItemRequestDTO itemDto : dto.getItems()) {
                PrescriptionItem item = toItemEntity(itemDto);
                prescription.addItem(item);
            }
        }

        return prescription;
    }

    public PrescriptionItem toItemEntity(PrescriptionItemRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        return PrescriptionItem.builder()
                .drugName(dto.getDrugName())
                .dosage(dto.getDosage())
                .notes(dto.getNotes())
                .build();
    }

    public void updateEntity(Prescription prescription, PrescriptionRequestDTO dto) {
        if (dto == null) return;

        // Kosongkan list lama dan isi dengan yang baru (karena CascadeType.ALL + orphanRemoval)
        prescription.getItems().clear();

        if (dto.getItems() != null) {
            for (PrescriptionItemRequestDTO itemDto : dto.getItems()) {
                PrescriptionItem item = toItemEntity(itemDto);
                prescription.addItem(item);
            }
        }
    }

    public PrescriptionResponseDTO toDto(Prescription entity) {
        if (entity == null) {
            return null;
        }

        return PrescriptionResponseDTO.builder()
                .id(entity.getId())
                .medicalRecordId(entity.getMedicalRecord() != null ? entity.getMedicalRecord().getId() : null)
                .prescriptionNumber(entity.getPrescriptionNumber())
                .status(entity.getStatus())
                .patientId(entity.getMedicalRecord() != null && entity.getMedicalRecord().getPatient() != null ? entity.getMedicalRecord().getPatient().getId() : null)
                .patientName(entity.getMedicalRecord() != null && entity.getMedicalRecord().getPatient() != null ? entity.getMedicalRecord().getPatient().getName() : null)
                .medicalRecordNumber(entity.getMedicalRecord() != null && entity.getMedicalRecord().getPatient() != null ? entity.getMedicalRecord().getPatient().getMedicalRecordNumber() : null)
                .doctorId(entity.getMedicalRecord() != null && entity.getMedicalRecord().getDoctor() != null ? entity.getMedicalRecord().getDoctor().getId() : null)
                .doctorName(entity.getMedicalRecord() != null && entity.getMedicalRecord().getDoctor() != null ? entity.getMedicalRecord().getDoctor().getName() : null)
                .items(toItemDtoList(entity.getItems()))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }

    public List<PrescriptionItemResponseDTO> toItemDtoList(List<PrescriptionItem> items) {
        if (items == null) {
            return null;
        }

        return items.stream()
                .map(this::toItemDto)
                .collect(Collectors.toList());
    }

    public PrescriptionItemResponseDTO toItemDto(PrescriptionItem entity) {
        if (entity == null) {
            return null;
        }

        return PrescriptionItemResponseDTO.builder()
                .id(entity.getId())
                .drugName(entity.getDrugName())
                .dosage(entity.getDosage())
                .notes(entity.getNotes())
                .build();
    }
}
