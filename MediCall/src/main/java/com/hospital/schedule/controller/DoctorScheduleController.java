package com.hospital.schedule.controller;

import com.hospital.common.dto.ApiResponse;
import com.hospital.schedule.dto.ScheduleRequestDTO;
import com.hospital.schedule.dto.ScheduleResponseDTO;
import com.hospital.schedule.service.DoctorScheduleService;
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
@RequestMapping("/api/v1/schedules")
@RequiredArgsConstructor
@Tag(name = "Master Data - Doctor Schedule", description = "API untuk mengelola Jadwal Praktik Dokter")
@SecurityRequirement(name = "BearerAuth")
public class DoctorScheduleController {

    private final DoctorScheduleService scheduleService;

    @Operation(summary = "Mendapatkan semua jadwal dokter dengan Pagination dan Pencarian")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE', 'PATIENT')")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<ScheduleResponseDTO>>> getAllSchedules(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String search
    ) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<ScheduleResponseDTO> schedules = scheduleService.getAllSchedules(search, pageable);
        return ResponseEntity.ok(ApiResponse.success("Jadwal dokter berhasil diambil", schedules));
    }

    @Operation(summary = "Mendapatkan jadwal dokter berdasarkan ID")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE', 'PATIENT')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ScheduleResponseDTO>> getScheduleById(@PathVariable Integer id) {
        ScheduleResponseDTO schedule = scheduleService.getScheduleById(id);
        return ResponseEntity.ok(ApiResponse.success("Jadwal dokter berhasil diambil", schedule));
    }

    @Operation(summary = "Menambahkan jadwal dokter baru (Khusus ADMIN)")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<ScheduleResponseDTO>> createSchedule(@Valid @RequestBody ScheduleRequestDTO request) {
        ScheduleResponseDTO createdSchedule = scheduleService.createSchedule(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Jadwal dokter berhasil dibuat", createdSchedule));
    }

    @Operation(summary = "Mengubah jadwal dokter berdasarkan ID (Khusus ADMIN)")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ScheduleResponseDTO>> updateSchedule(
            @PathVariable Integer id,
            @Valid @RequestBody ScheduleRequestDTO request) {
        ScheduleResponseDTO updatedSchedule = scheduleService.updateSchedule(id, request);
        return ResponseEntity.ok(ApiResponse.success("Jadwal dokter berhasil diupdate", updatedSchedule));
    }

    @Operation(summary = "Menghapus jadwal dokter (Soft Delete, Khusus ADMIN)")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteSchedule(@PathVariable Integer id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.ok(ApiResponse.success("Jadwal dokter berhasil dihapus", null));
    }
}
