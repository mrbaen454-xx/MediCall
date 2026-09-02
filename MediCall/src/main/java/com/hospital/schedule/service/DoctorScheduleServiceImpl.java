package com.hospital.schedule.service;

import com.hospital.doctor.entity.Doctor;
import com.hospital.doctor.repository.DoctorRepository;
import com.hospital.schedule.dto.ScheduleRequestDTO;
import com.hospital.schedule.dto.ScheduleResponseDTO;
import com.hospital.schedule.entity.DoctorSchedule;
import com.hospital.schedule.mapper.ScheduleMapper;
import com.hospital.schedule.repository.DoctorScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DoctorScheduleServiceImpl implements DoctorScheduleService {

    private final DoctorScheduleRepository scheduleRepository;
    private final DoctorRepository doctorRepository;
    private final ScheduleMapper scheduleMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ScheduleResponseDTO> getAllSchedules(String search, Pageable pageable) {
        Specification<DoctorSchedule> spec = Specification.where(null);
        
        if (search != null && !search.trim().isEmpty()) {
            String searchLower = "%" + search.toLowerCase() + "%";
            spec = spec.and((root, query, criteriaBuilder) -> 
                criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.join("doctor").get("name")), searchLower),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("dayOfWeek").as(String.class)), searchLower),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), searchLower)
                )
            );
        }

        Page<DoctorSchedule> schedules = scheduleRepository.findAll(spec, pageable);
        return schedules.map(scheduleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ScheduleResponseDTO getScheduleById(Integer id) {
        DoctorSchedule schedule = findScheduleByIdOrThrow(id);
        return scheduleMapper.toDto(schedule);
    }

    @Override
    @Transactional
    public ScheduleResponseDTO createSchedule(ScheduleRequestDTO requestDTO) {
        validateTimeRange(requestDTO);
        validateOverlap(requestDTO, null);

        Doctor doctor = findDoctorOrThrow(requestDTO.getDoctorId());

        DoctorSchedule schedule = scheduleMapper.toEntity(requestDTO, doctor);
        schedule = scheduleRepository.save(schedule);
        
        return scheduleMapper.toDto(schedule);
    }

    @Override
    @Transactional
    public ScheduleResponseDTO updateSchedule(Integer id, ScheduleRequestDTO requestDTO) {
        DoctorSchedule schedule = findScheduleByIdOrThrow(id);
        
        validateTimeRange(requestDTO);
        validateOverlap(requestDTO, id);

        Doctor doctor = null;
        if (!requestDTO.getDoctorId().equals(schedule.getDoctor().getId())) {
            doctor = findDoctorOrThrow(requestDTO.getDoctorId());
        }

        scheduleMapper.updateEntity(schedule, requestDTO, doctor);
        schedule = scheduleRepository.save(schedule);
        
        return scheduleMapper.toDto(schedule);
    }

    @Override
    @Transactional
    public void deleteSchedule(Integer id) {
        DoctorSchedule schedule = findScheduleByIdOrThrow(id);
        scheduleRepository.delete(schedule);
    }

    private DoctorSchedule findScheduleByIdOrThrow(Integer id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jadwal dengan ID " + id + " tidak ditemukan"));
    }

    private Doctor findDoctorOrThrow(Integer id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor dengan ID " + id + " tidak ditemukan"));
    }

    private void validateTimeRange(ScheduleRequestDTO dto) {
        if (!dto.getStartTime().isBefore(dto.getEndTime())) {
            throw new RuntimeException("Jam mulai (" + dto.getStartTime() + ") harus lebih awal dari jam selesai (" + dto.getEndTime() + ")");
        }
    }

    private void validateOverlap(ScheduleRequestDTO dto, Integer excludeScheduleId) {
        long overlapCount = scheduleRepository.countOverlappingSchedules(
                dto.getDoctorId(),
                dto.getDayOfWeek(),
                dto.getStartTime(),
                dto.getEndTime(),
                excludeScheduleId
        );

        if (overlapCount > 0) {
            throw new RuntimeException("Jadwal dokter bentrok pada hari " + dto.getDayOfWeek() + " antara jam " + dto.getStartTime() + " - " + dto.getEndTime());
        }
    }
}
