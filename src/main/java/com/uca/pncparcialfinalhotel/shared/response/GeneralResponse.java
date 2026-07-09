package com.uca.pncparcialfinalhotel.shared.response;

import lombok.Builder;

@Builder
public record GeneralResponse<T>(
        String message,
        T data
) {
    public static <T> GeneralResponse<T> of(String message, T data) {
        return new GeneralResponse<>(message, data);
    }

    public static <T> GeneralResponse<T> of(String message) {
        return new GeneralResponse<>(message, null);
    }
}