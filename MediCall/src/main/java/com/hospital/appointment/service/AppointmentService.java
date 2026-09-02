package com.hospital.appointment.service;

import com.hospital.appointment.dto.AppointmentRequestDTO;
import com.hospital.appointment.dto.AppointmentResponseDTO;
import com.hospital.appointment.dto.UpdateAppointmentStatusDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AppointmentService {
    Page<AppointmentResponseDTO> getAllAppointments(String search, Pageable pageable);
    AppointmentResponseDTO getAppointmentById(Integer id);
    AppointmentResponseDTO createAppointment(AppointmentRequestDTO requestDTO);
    AppointmentResponseDTO updateAppointmentStatus(Integer id, UpdateAppointmentStatusDTO requestDTO);
    void deleteAppointment(Integer id);
}
