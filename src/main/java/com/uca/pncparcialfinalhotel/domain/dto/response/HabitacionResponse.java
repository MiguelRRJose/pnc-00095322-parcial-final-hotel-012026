package com.uca.pncparcialfinalhotel.domain.dto.response;

import com.uca.pncparcialfinalhotel.domain.enums.TipoHabitacion;

import java.math.BigDecimal;

public record HabitacionResponse(
        Long id,
        String numero,
        TipoHabitacion tipo,
        BigDecimal precioPorNoche,
        Boolean disponible,
        Long hotelId,
        String hotelNombre
) {}