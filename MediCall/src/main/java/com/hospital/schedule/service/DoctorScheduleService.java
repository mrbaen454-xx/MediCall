package com.hospital.schedule.service;

import com.hospital.schedule.dto.ScheduleRequestDTO;
import com.hospital.schedule.dto.ScheduleResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DoctorScheduleService {
    Page<ScheduleResponseDTO> getAllSchedules(String search, Pageable pageable);
    ScheduleResponseDTO getScheduleById(Integer id);
    ScheduleResponseDTO createSchedule(ScheduleRequestDTO requestDTO);
    ScheduleResponseDTO updateSchedule(Integer id, ScheduleRequestDTO requestDTO);
    void deleteSchedule(Integer id);
}
