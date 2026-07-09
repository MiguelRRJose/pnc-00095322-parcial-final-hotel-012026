package com.uca.pncparcialfinalhotel.domain.dto.response;

import com.uca.pncparcialfinalhotel.domain.enums.Rol;

public record UsuarioResponse(
        Long id,
        String username,
        String nombre,
        String email,
        Rol rol,
        Long hotelId,
        String hotelNombre
) {}