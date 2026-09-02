package com.hospital.patient.controller;

import com.hospital.common.dto.ApiResponse;
import com.hospital.patient.dto.PatientRequestDTO;
import com.hospital.patient.dto.PatientResponseDTO;
import com.hospital.patient.service.PatientService;
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
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
@Tag(name = "Master Data - Patient", description = "API untuk mengelola data Pasien")
@SecurityRequirement(name = "BearerAuth")
public class PatientController {

    private final PatientService patientService;

    @Operation(summary = "Mendapatkan semua data Patient dengan Pagination dan Pencarian")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE')")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<PatientResponseDTO>>> getAllPatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) String search
    ) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<PatientResponseDTO> patients = patientService.getAllPatients(search, pageable);
        return ResponseEntity.ok(ApiResponse.success("Data pasien berhasil diambil", patients));
    }

    @Operation(summary = "Mendapatkan data Patient berdasarkan ID")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PatientResponseDTO>> getPatientById(@PathVariable Integer id) {
        PatientResponseDTO patient = patientService.getPatientById(id);
        return ResponseEntity.ok(ApiResponse.success("Data pasien berhasil diambil", patient));
    }

    @Operation(summary = "Menambahkan data Patient baru (ADMIN / NURSE)")
    @PreAuthorize("hasAnyRole('ADMIN', 'NURSE')")
    @PostMapping
    public ResponseEntity<ApiResponse<PatientResponseDTO>> createPatient(@Valid @RequestBody PatientRequestDTO request) {
        PatientResponseDTO createdPatient = patientService.createPatient(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Pasien berhasil didaftarkan", createdPatient));
    }

    @Operation(summary = "Mengubah data Patient berdasarkan ID (ADMIN / NURSE)")
    @PreAuthorize("hasAnyRole('ADMIN', 'NURSE')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PatientResponseDTO>> updatePatient(
            @PathVariable Integer id,
            @Valid @RequestBody PatientRequestDTO request) {
        PatientResponseDTO updatedPatient = patientService.updatePatient(id, request);
        return ResponseEntity.ok(ApiResponse.success("Data pasien berhasil diupdate", updatedPatient));
    }

    @Operation(summary = "Menghapus data Patient (Soft Delete, Khusus ADMIN)")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deletePatient(@PathVariable Integer id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok(ApiResponse.success("Data pasien berhasil dihapus", null));
    }
}
