package com.uca.pncparcialfinalhotel.services;

import com.uca.pncparcialfinalhotel.domain.dto.request.LoginRequest;
import com.uca.pncparcialfinalhotel.domain.dto.request.RefreshTokenRequest;
import com.uca.pncparcialfinalhotel.domain.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse login(LoginRequest request);
    AuthResponse refresh(RefreshTokenRequest request);
}