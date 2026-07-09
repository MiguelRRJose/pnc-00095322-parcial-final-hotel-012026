package com.uca.pncparcialfinalhotel.controllers;

import com.uca.pncparcialfinalhotel.domain.dto.request.LoginRequest;
import com.uca.pncparcialfinalhotel.domain.dto.request.RefreshTokenRequest;
import com.uca.pncparcialfinalhotel.domain.dto.response.AuthResponse;
import com.uca.pncparcialfinalhotel.services.AuthService;
import com.uca.pncparcialfinalhotel.shared.response.GeneralResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<GeneralResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(GeneralResponse.of("Login exitoso", response));
    }

    @PostMapping("/refresh")
    public ResponseEntity<GeneralResponse<AuthResponse>> refresh(
            @Valid @RequestBody RefreshTokenRequest request) {
        AuthResponse response = authService.refresh(request);
        return ResponseEntity.ok(GeneralResponse.of("Token renovado exitosamente", response));
    }
}