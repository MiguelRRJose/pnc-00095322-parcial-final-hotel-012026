package com.uca.pncparcialfinalhotel.domain.dto.response;

import com.uca.pncparcialfinalhotel.domain.enums.EstadoReserva;

import java.time.LocalDate;

public record ReservaResponse(
        Long id,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        EstadoReserva estado,
        Long huespedId,
        String huespedNombre,
        Long habitacionId,
        String habitacionNumero,
        Long hotelId,
        String hotelNombre
) {}