package com.hospital.schedule.repository;

import com.hospital.schedule.entity.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Repository
public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Integer>, JpaSpecificationExecutor<DoctorSchedule> {

    @Query("SELECT COUNT(s) FROM DoctorSchedule s " +
           "WHERE s.doctor.id = :doctorId " +
           "AND s.dayOfWeek = :dayOfWeek " +
           "AND s.isDeleted = false " +
           "AND (s.id != :excludeId OR :excludeId IS NULL) " +
           "AND (:startTime < s.endTime AND :endTime > s.startTime)")
    long countOverlappingSchedules(
            @Param("doctorId") Integer doctorId,
            @Param("dayOfWeek") DayOfWeek dayOfWeek,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime,
            @Param("excludeId") Integer excludeId
    );
}
