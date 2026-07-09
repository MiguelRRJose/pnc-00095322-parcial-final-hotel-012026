package com.uca.pncparcialfinalhotel.domain.dto.response;

public record HotelResponse(
        Long id,
        String nombre,
        String direccion,
        String ciudad
) {}