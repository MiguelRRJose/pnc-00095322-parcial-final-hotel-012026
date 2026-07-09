package com.uca.pncparcialfinalhotel.domain.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CrearHotelRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @NotBlank(message = "La dirección es obligatoria")
        String direccion,

        @NotBlank(message = "La ciudad es obligatoria")
        String ciudad
) {}