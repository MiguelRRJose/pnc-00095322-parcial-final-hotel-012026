package com.uca.pncparcialfinalhotel.domain.dto.request;

import com.uca.pncparcialfinalhotel.domain.enums.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearUsuarioRequest(
        @NotBlank(message = "El username es obligatorio")
        String username,

        @NotBlank(message = "La contraseña es obligatoria")
        String password,

        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @Email(message = "Email inválido")
        @NotBlank(message = "El email es obligatorio")
        String email,

        @NotNull(message = "El rol es obligatorio")
        Rol rol,

        // Solo requerido si rol == RECEPCIONISTA
        Long hotelId
) {}