package com.hospital.medicalrecord.controller;

import com.hospital.common.dto.ApiResponse;
import com.hospital.medicalrecord.dto.MedicalRecordRequestDTO;
import com.hospital.medicalrecord.dto.MedicalRecordResponseDTO;
import com.hospital.medicalrecord.service.MedicalRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/medical-records")
@RequiredArgsConstructor
@Tag(name = "Core Transaction - Medical Record", description = "API untuk rekam medis pasien")
@SecurityRequirement(name = "BearerAuth")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @Operation(summary = "Mendapatkan semua rekam medis dengan Pagination dan Pencarian")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE', 'PATIENT')")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<MedicalRecordResponseDTO>>> getAllMedicalRecords(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) String search
    ) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<MedicalRecordResponseDTO> records = medicalRecordService.getAllMedicalRecords(search, pageable);
        return ResponseEntity.ok(ApiResponse.success("Data rekam medis berhasil diambil", records));
    }

    @Operation(summary = "Mendapatkan data Rekam Medis berdasarkan ID")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE', 'PATIENT')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MedicalRecordResponseDTO>> getMedicalRecordById(@PathVariable Integer id) {
        MedicalRecordResponseDTO record = medicalRecordService.getMedicalRecordById(id);
        return ResponseEntity.ok(ApiResponse.success("Data rekam medis berhasil diambil", record));
    }

    @Operation(summary = "Menambahkan Rekam Medis baru (Khusus DOCTOR / ADMIN)")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    @PostMapping
    public ResponseEntity<ApiResponse<MedicalRecordResponseDTO>> createMedicalRecord(@Valid @RequestBody MedicalRecordRequestDTO request) {
        MedicalRecordResponseDTO createdRecord = medicalRecordService.createMedicalRecord(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Rekam Medis berhasil disimpan", createdRecord));
    }

    @Operation(summary = "Mengubah Rekam Medis berdasarkan ID (Khusus DOCTOR / ADMIN)")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MedicalRecordResponseDTO>> updateMedicalRecord(
            @PathVariable Integer id,
            @Valid @RequestBody MedicalRecordRequestDTO request) {
        MedicalRecordResponseDTO updatedRecord = medicalRecordService.updateMedicalRecord(id, request);
        return ResponseEntity.ok(ApiResponse.success("Rekam Medis berhasil diupdate", updatedRecord));
    }

    @Operation(summary = "Menghapus Rekam Medis (Soft Delete, Khusus ADMIN)")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteMedicalRecord(@PathVariable Integer id) {
        medicalRecordService.deleteMedicalRecord(id);
        return ResponseEntity.ok(ApiResponse.success("Rekam Medis berhasil dihapus", null));
    }
}
