package com.hospital.department.controller;

import com.hospital.common.dto.ApiResponse;
import com.hospital.department.dto.DepartmentRequestDTO;
import com.hospital.department.dto.DepartmentResponseDTO;
import com.hospital.department.service.DepartmentService;
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
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
@Tag(name = "Master Data - Department", description = "API untuk mengelola data Poli (Department)")
@SecurityRequirement(name = "BearerAuth")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Operation(summary = "Mendapatkan semua data Department dengan Pagination dan Pencarian")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE')")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<DepartmentResponseDTO>>> getAllDepartments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String search
    ) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<DepartmentResponseDTO> departments = departmentService.getAllDepartments(search, pageable);
        return ResponseEntity.ok(ApiResponse.success("Data berhasil diambil", departments));
    }

    @Operation(summary = "Mendapatkan data Department berdasarkan ID")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponseDTO>> getDepartmentById(@PathVariable Integer id) {
        DepartmentResponseDTO department = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(ApiResponse.success("Data berhasil diambil", department));
    }

    @Operation(summary = "Menambahkan data Department baru (Khusus ADMIN)")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<DepartmentResponseDTO>> createDepartment(@Valid @RequestBody DepartmentRequestDTO request) {
        DepartmentResponseDTO createdDepartment = departmentService.createDepartment(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Department berhasil dibuat", createdDepartment));
    }

    @Operation(summary = "Mengubah data Department berdasarkan ID (Khusus ADMIN)")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponseDTO>> updateDepartment(
            @PathVariable Integer id,
            @Valid @RequestBody DepartmentRequestDTO request) {
        DepartmentResponseDTO updatedDepartment = departmentService.updateDepartment(id, request);
        return ResponseEntity.ok(ApiResponse.success("Department berhasil diupdate", updatedDepartment));
    }

    @Operation(summary = "Menghapus data Department (Soft Delete, Khusus ADMIN)")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteDepartment(@PathVariable Integer id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.ok(ApiResponse.success("Department berhasil dihapus", null));
    }
}
