package com.hospital.schedule.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class ScheduleRequestDTO {

    @NotNull(message = "Doctor ID tidak boleh kosong")
    private Integer doctorId;

    @NotNull(message = "Hari (Day Of Week) tidak boleh kosong")
    private DayOfWeek dayOfWeek;

    @NotNull(message = "Jam Mulai (Start Time) tidak boleh kosong")
    private LocalTime startTime;

    @NotNull(message = "Jam Selesai (End Time) tidak boleh kosong")
    private LocalTime endTime;

    @NotNull(message = "Kuota tidak boleh kosong")
    @Min(value = 1, message = "Kuota minimal 1")
    private Integer quota;

    private String status;
}
