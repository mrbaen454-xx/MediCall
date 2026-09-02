package com.hospital.controller;

import com.hospital.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller ini HANYA sebagai contoh implementasi Authorization (RBAC).
 * Tidak ada logika bisnis di dalamnya.
 */
@RestController
@RequestMapping("/api/demo")
public class DemoAuthorizationController {

    // 1. Endpoint yang bisa diakses oleh siapa saja yang sudah login
    @GetMapping("/public-info")
    public ResponseEntity<ApiResponse<String>> getPublicInfo() {
        return ResponseEntity.ok(ApiResponse.success("Berhasil diakses", "Semua user login bisa melihat ini."));
    }

    // 2. Endpoint khusus ADMIN (Menggunakan Method Security)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin-only")
    public ResponseEntity<ApiResponse<String>> getAdminData() {
        return ResponseEntity.ok(ApiResponse.success("Berhasil diakses", "Data Rahasia Admin"));
    }

    // 3. Endpoint untuk DOCTOR
    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/doctor-only")
    public ResponseEntity<ApiResponse<String>> getDoctorData() {
        return ResponseEntity.ok(ApiResponse.success("Berhasil diakses", "Data Rekam Medis (Khusus Dokter)"));
    }

    // 4. Endpoint yang bisa diakses oleh DOCTOR atau NURSE
    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE')")
    @GetMapping("/medical-staff")
    public ResponseEntity<ApiResponse<String>> getMedicalStaffData() {
        return ResponseEntity.ok(ApiResponse.success("Berhasil diakses", "Data Pasien Rawat Inap (Dokter & Perawat)"));
    }

    // 5. Endpoint untuk PHARMACIST
    @PreAuthorize("hasRole('PHARMACIST')")
    @GetMapping("/pharmacist-only")
    public ResponseEntity<ApiResponse<String>> getPharmacyData() {
        return ResponseEntity.ok(ApiResponse.success("Berhasil diakses", "Data Stok Obat (Khusus Apoteker)"));
    }
}
