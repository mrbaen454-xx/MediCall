package com.hospital.auth.controller;

import com.hospital.auth.dto.AuthResponse;
import com.hospital.auth.dto.LoginRequest;
import com.hospital.auth.dto.RegisterRequest;
import com.hospital.auth.dto.TokenRefreshRequest;
import com.hospital.auth.entity.RefreshToken;
import com.hospital.auth.service.AuthenticationService;
import com.hospital.auth.service.RefreshTokenService;
import com.hospital.common.dto.ApiResponse;
import com.hospital.security.CustomUserDetails;
import com.hospital.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authenticationService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Login berhasil", response));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authenticationService.register(request);
        return ResponseEntity.ok(ApiResponse.success("Registrasi user berhasil", response));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<AuthResponse>> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        return refreshTokenService.findByToken(request.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtService.generateToken(new CustomUserDetails(user));
                    
                    AuthResponse authResponse = AuthResponse.builder()
                            .accessToken(token)
                            .refreshToken(request.getRefreshToken())
                            .build();
                            
                    return ResponseEntity.ok(ApiResponse.success("Token berhasil diperbarui", authResponse));
                })
                .orElseThrow(() -> new RuntimeException("Refresh token tidak valid di database"));
    }
}
