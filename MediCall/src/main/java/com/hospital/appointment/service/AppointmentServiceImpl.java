package com.hospital.appointment.service;

import com.hospital.appointment.dto.AppointmentRequestDTO;
import com.hospital.appointment.dto.AppointmentResponseDTO;
import com.hospital.appointment.dto.UpdateAppointmentStatusDTO;
import com.hospital.appointment.entity.Appointment;
import com.hospital.appointment.mapper.AppointmentMapper;
import com.hospital.appointment.repository.AppointmentRepository;
import com.hospital.doctor.entity.Doctor;
import com.hospital.doctor.repository.DoctorRepository;
import com.hospital.patient.entity.Patient;
import com.hospital.patient.repository.PatientRepository;
import com.hospital.schedule.entity.DoctorSchedule;
import com.hospital.schedule.repository.DoctorScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final DoctorScheduleRepository scheduleRepository;
    private final AppointmentMapper appointmentMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<AppointmentResponseDTO> getAllAppointments(String search, Pageable pageable) {
        Specification<Appointment> spec = Specification.where(null);
        
        if (search != null && !search.trim().isEmpty()) {
            String searchLower = "%" + search.toLowerCase() + "%";
            spec = spec.and((root, query, criteriaBuilder) -> 
                criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.join("patient").get("name")), searchLower),
                    criteriaBuilder.like(criteriaBuilder.lower(root.join("patient").get("medicalRecordNumber")), searchLower),
                    criteriaBuilder.like(criteriaBuilder.lower(root.join("doctor").get("name")), searchLower),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), searchLower)
                )
            );
        }

        Page<Appointment> appointments = appointmentRepository.findAll(spec, pageable);
        return appointments.map(appointmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public AppointmentResponseDTO getAppointmentById(Integer id) {
        Appointment appointment = findAppointmentByIdOrThrow(id);
        return appointmentMapper.toDto(appointment);
    }

    @Override
    @Transactional
    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO requestDTO) {
        Patient patient = patientRepository.findById(requestDTO.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient tidak ditemukan"));
                
        Doctor doctor = doctorRepository.findById(requestDTO.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor tidak ditemukan"));

        DayOfWeek requestedDay = requestDTO.getAppointmentDate().getDayOfWeek();
        
        // Cari jadwal dokter pada hari tersebut
        List<DoctorSchedule> schedules = scheduleRepository.findAll((root, query, cb) -> 
            cb.and(
                cb.equal(root.get("doctor").get("id"), doctor.getId()),
                cb.equal(root.get("dayOfWeek"), requestedDay),
                cb.equal(root.get("status"), "ACTIVE"),
                cb.equal(root.get("isDeleted"), false)
            )
        );

        if (schedules.isEmpty()) {
            throw new RuntimeException("Dokter " + doctor.getName() + " tidak memiliki jadwal aktif pada hari " + requestedDay);
        }

        // Cari jadwal yang jam nya mencakup waktu yang diminta pasien
        DoctorSchedule matchedSchedule = schedules.stream()
                .filter(s -> !requestDTO.getAppointmentTime().isBefore(s.getStartTime()) && 
                             !requestDTO.getAppointmentTime().isAfter(s.getEndTime()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Jam " + requestDTO.getAppointmentTime() + 
                        " berada di luar jam operasional dokter pada hari " + requestedDay));

        // Cek Kuota
        long currentAppointments = appointmentRepository.countActiveAppointmentsByScheduleAndDate(
                matchedSchedule.getId(), requestDTO.getAppointmentDate());
                
        if (currentAppointments >= matchedSchedule.getQuota()) {
            throw new RuntimeException("Kuota dokter untuk tanggal " + requestDTO.getAppointmentDate() + " sudah penuh");
        }

        // Generate Queue Number
        int maxQueue = appointmentRepository.findMaxQueueNumberByScheduleAndDate(
                matchedSchedule.getId(), requestDTO.getAppointmentDate()).orElse(0);
        int nextQueueNumber = maxQueue + 1;

        Appointment appointment = appointmentMapper.toEntity(requestDTO, patient, doctor, matchedSchedule, nextQueueNumber);
        appointment = appointmentRepository.save(appointment);
        
        return appointmentMapper.toDto(appointment);
    }

    @Override
    @Transactional
    public AppointmentResponseDTO updateAppointmentStatus(Integer id, UpdateAppointmentStatusDTO requestDTO) {
        Appointment appointment = findAppointmentByIdOrThrow(id);
        
        // Validasi transisi status bisa ditambahkan di sini jika diperlukan
        appointment.setStatus(requestDTO.getStatus());
        appointment = appointmentRepository.save(appointment);
        
        return appointmentMapper.toDto(appointment);
    }

    @Override
    @Transactional
    public void deleteAppointment(Integer id) {
        Appointment appointment = findAppointmentByIdOrThrow(id);
        appointmentRepository.delete(appointment);
    }

    private Appointment findAppointmentByIdOrThrow(Integer id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment dengan ID " + id + " tidak ditemukan"));
    }
}
