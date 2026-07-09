package com.uca.pncparcialfinalhotel.domain.dto.request;

import com.uca.pncparcialfinalhotel.domain.enums.TipoHabitacion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CrearHabitacionRequest(
        @NotBlank(message = "El número de habitación es obligatorio")
        String numero,

        @NotNull(message = "El tipo es obligatorio")
        TipoHabitacion tipo,

        @NotNull(message = "El precio por noche es obligatorio")
        @Positive(message = "El precio debe ser mayor a 0")
        BigDecimal precioPorNoche,

        @NotNull(message = "El hotel es obligatorio")
        Long hotelId
) {}