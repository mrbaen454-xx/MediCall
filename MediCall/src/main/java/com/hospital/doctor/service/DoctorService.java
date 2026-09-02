package com.hospital.doctor.service;

import com.hospital.doctor.dto.DoctorRequestDTO;
import com.hospital.doctor.dto.DoctorResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DoctorService {
    Page<DoctorResponseDTO> getAllDoctors(String search, Pageable pageable);
    DoctorResponseDTO getDoctorById(Integer id);
    DoctorResponseDTO createDoctor(DoctorRequestDTO requestDTO);
    DoctorResponseDTO updateDoctor(Integer id, DoctorRequestDTO requestDTO);
    void deleteDoctor(Integer id);
}
