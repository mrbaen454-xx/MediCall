package com.hospital.department.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DepartmentRequestDTO {
    
    @NotBlank(message = "Nama Poli (Department) tidak boleh kosong")
    private String name;

    private String description;
}
