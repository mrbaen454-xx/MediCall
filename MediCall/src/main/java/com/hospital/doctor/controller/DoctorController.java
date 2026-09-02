package com.hospital.doctor.controller;

import com.hospital.common.dto.ApiResponse;
import com.hospital.doctor.dto.DoctorRequestDTO;
import com.hospital.doctor.dto.DoctorResponseDTO;
import com.hospital.doctor.service.DoctorService;
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
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
@Tag(name = "Master Data - Doctor", description = "API untuk mengelola data Dokter")
@SecurityRequirement(name = "BearerAuth")
public class DoctorController {

    private final DoctorService doctorService;

    @Operation(summary = "Mendapatkan semua data Doctor dengan Pagination dan Pencarian")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE', 'PATIENT')")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<DoctorResponseDTO>>> getAllDoctors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String search
    ) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<DoctorResponseDTO> doctors = doctorService.getAllDoctors(search, pageable);
        return ResponseEntity.ok(ApiResponse.success("Data dokter berhasil diambil", doctors));
    }

    @Operation(summary = "Mendapatkan data Doctor berdasarkan ID")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE', 'PATIENT')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorResponseDTO>> getDoctorById(@PathVariable Integer id) {
        DoctorResponseDTO doctor = doctorService.getDoctorById(id);
        return ResponseEntity.ok(ApiResponse.success("Data dokter berhasil diambil", doctor));
    }

    @Operation(summary = "Menambahkan data Doctor baru (Khusus ADMIN)")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<DoctorResponseDTO>> createDoctor(@Valid @RequestBody DoctorRequestDTO request) {
        DoctorResponseDTO createdDoctor = doctorService.createDoctor(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Dokter berhasil ditambahkan", createdDoctor));
    }

    @Operation(summary = "Mengubah data Doctor berdasarkan ID (Khusus ADMIN)")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorResponseDTO>> updateDoctor(
            @PathVariable Integer id,
            @Valid @RequestBody DoctorRequestDTO request) {
        DoctorResponseDTO updatedDoctor = doctorService.updateDoctor(id, request);
        return ResponseEntity.ok(ApiResponse.success("Data dokter berhasil diupdate", updatedDoctor));
    }

    @Operation(summary = "Menghapus data Doctor (Soft Delete, Khusus ADMIN)")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteDoctor(@PathVariable Integer id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok(ApiResponse.success("Data dokter berhasil dihapus", null));
    }
}
