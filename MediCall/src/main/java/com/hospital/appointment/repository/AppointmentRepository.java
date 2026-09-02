package com.hospital.appointment.repository;

import com.hospital.appointment.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer>, JpaSpecificationExecutor<Appointment> {

    @Query("SELECT COUNT(a) FROM Appointment a " +
           "WHERE a.schedule.id = :scheduleId " +
           "AND a.appointmentDate = :appointmentDate " +
           "AND a.status != 'CANCELLED' " +
           "AND a.isDeleted = false")
    long countActiveAppointmentsByScheduleAndDate(
            @Param("scheduleId") Integer scheduleId,
            @Param("appointmentDate") LocalDate appointmentDate
    );

    @Query("SELECT MAX(a.queueNumber) FROM Appointment a " +
           "WHERE a.schedule.id = :scheduleId " +
           "AND a.appointmentDate = :appointmentDate " +
           "AND a.isDeleted = false")
    Optional<Integer> findMaxQueueNumberByScheduleAndDate(
            @Param("scheduleId") Integer scheduleId,
            @Param("appointmentDate") LocalDate appointmentDate
    );
}
