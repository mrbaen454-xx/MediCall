package com.hospital.prescription.controller;

import com.hospital.common.dto.ApiResponse;
import com.hospital.prescription.dto.PrescriptionRequestDTO;
import com.hospital.prescription.dto.PrescriptionResponseDTO;
import com.hospital.prescription.dto.UpdatePrescriptionStatusDTO;
import com.hospital.prescription.service.PrescriptionService;
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
@RequestMapping("/api/v1/prescriptions")
@RequiredArgsConstructor
@Tag(name = "Core Transaction - Prescription", description = "API untuk resep obat pasien")
@SecurityRequirement(name = "BearerAuth")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @Operation(summary = "Mendapatkan semua resep obat dengan Pagination dan Pencarian")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'PHARMACIST', 'PATIENT')")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<PrescriptionResponseDTO>>> getAllPrescriptions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) String search
    ) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<PrescriptionResponseDTO> prescriptions = prescriptionService.getAllPrescriptions(search, pageable);
        return ResponseEntity.ok(ApiResponse.success("Data resep obat berhasil diambil", prescriptions));
    }

    @Operation(summary = "Mendapatkan resep obat berdasarkan ID (Termasuk detail obat)")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'PHARMACIST', 'PATIENT')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PrescriptionResponseDTO>> getPrescriptionById(@PathVariable Integer id) {
        PrescriptionResponseDTO prescription = prescriptionService.getPrescriptionById(id);
        return ResponseEntity.ok(ApiResponse.success("Data resep obat berhasil diambil", prescription));
    }

    @Operation(summary = "Menambahkan Lembar Resep Baru (Khusus DOCTOR / ADMIN)")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    @PostMapping
    public ResponseEntity<ApiResponse<PrescriptionResponseDTO>> createPrescription(@Valid @RequestBody PrescriptionRequestDTO request) {
        PrescriptionResponseDTO createdPrescription = prescriptionService.createPrescription(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Resep obat berhasil dibuat dengan nomor: " + createdPrescription.getPrescriptionNumber(), createdPrescription));
    }

    @Operation(summary = "Mengubah isi Resep Obat (Hanya bisa jika status PENDING)")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PrescriptionResponseDTO>> updatePrescription(
            @PathVariable Integer id,
            @Valid @RequestBody PrescriptionRequestDTO request) {
        PrescriptionResponseDTO updatedPrescription = prescriptionService.updatePrescription(id, request);
        return ResponseEntity.ok(ApiResponse.success("Isi resep obat berhasil diupdate", updatedPrescription));
    }

    @Operation(summary = "Memproses status Resep (PHARMACIST: PENDING -> PROCESSING -> COMPLETED)")
    @PreAuthorize("hasAnyRole('ADMIN', 'PHARMACIST')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<PrescriptionResponseDTO>> updateStatus(
            @PathVariable Integer id,
            @Valid @RequestBody UpdatePrescriptionStatusDTO request) {
        PrescriptionResponseDTO updatedPrescription = prescriptionService.updatePrescriptionStatus(id, request);
        return ResponseEntity.ok(ApiResponse.success("Status resep obat berhasil diupdate", updatedPrescription));
    }

    @Operation(summary = "Menghapus Resep Obat (Hanya bisa jika status PENDING)")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deletePrescription(@PathVariable Integer id) {
        prescriptionService.deletePrescription(id);
        return ResponseEntity.ok(ApiResponse.success("Resep obat berhasil dihapus", null));
    }
}
