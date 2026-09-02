package com.hospital.appointment.controller;

import com.hospital.appointment.dto.AppointmentRequestDTO;
import com.hospital.appointment.dto.AppointmentResponseDTO;
import com.hospital.appointment.dto.UpdateAppointmentStatusDTO;
import com.hospital.appointment.service.AppointmentService;
import com.hospital.common.dto.ApiResponse;
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
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
@Tag(name = "Core Transaction - Appointment", description = "API untuk reservasi dan antrian Pasien")
@SecurityRequirement(name = "BearerAuth")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Operation(summary = "Mendapatkan semua antrian/appointment dengan Pagination dan Pencarian")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE', 'PATIENT')")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<AppointmentResponseDTO>>> getAllAppointments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "appointmentDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) String search
    ) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<AppointmentResponseDTO> appointments = appointmentService.getAllAppointments(search, pageable);
        return ResponseEntity.ok(ApiResponse.success("Data appointment berhasil diambil", appointments));
    }

    @Operation(summary = "Mendapatkan data Appointment berdasarkan ID")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE', 'PATIENT')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AppointmentResponseDTO>> getAppointmentById(@PathVariable Integer id) {
        AppointmentResponseDTO appointment = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(ApiResponse.success("Data appointment berhasil diambil", appointment));
    }

    @Operation(summary = "Mendaftarkan pasien baru ke dokter (Booking Antrian)")
    @PreAuthorize("hasAnyRole('ADMIN', 'NURSE', 'PATIENT')")
    @PostMapping
    public ResponseEntity<ApiResponse<AppointmentResponseDTO>> createAppointment(@Valid @RequestBody AppointmentRequestDTO request) {
        AppointmentResponseDTO createdAppointment = appointmentService.createAppointment(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Berhasil mendaftar antrian", createdAppointment));
    }

    @Operation(summary = "Mengubah STATUS appointment (Contoh: WAITING -> ONGOING -> DONE)")
    @PreAuthorize("hasAnyRole('ADMIN', 'NURSE', 'DOCTOR')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<AppointmentResponseDTO>> updateStatus(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateAppointmentStatusDTO request) {
        AppointmentResponseDTO updatedAppointment = appointmentService.updateAppointmentStatus(id, request);
        return ResponseEntity.ok(ApiResponse.success("Status antrian berhasil diupdate", updatedAppointment));
    }

    @Operation(summary = "Membatalkan/Menghapus Appointment (Soft Delete)")
    @PreAuthorize("hasAnyRole('ADMIN', 'NURSE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteAppointment(@PathVariable Integer id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.ok(ApiResponse.success("Appointment berhasil dibatalkan", null));
    }
}
