package com.hospital.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class RegisterRequest {
    
    @NotBlank(message = "Username tidak boleh kosong")
    @Size(min = 4, max = 50, message = "Username minimal 4 karakter dan maksimal 50 karakter")
    private String username;

    @NotBlank(message = "Password tidak boleh kosong")
    @Size(min = 6, message = "Password minimal 6 karakter")
    private String password;

    @NotBlank(message = "Nama tidak boleh kosong")
    private String name;

    private Set<String> roles;
}
