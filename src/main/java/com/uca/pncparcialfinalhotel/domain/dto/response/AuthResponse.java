package com.uca.pncparcialfinalhotel.domain.dto.response;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        String username,
        String rol
) {}