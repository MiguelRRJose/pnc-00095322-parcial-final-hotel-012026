package com.uca.pncparcialfinalhotel.controllers;

import com.uca.pncparcialfinalhotel.domain.dto.request.CrearReservaRequest;
import com.uca.pncparcialfinalhotel.domain.dto.response.ReservaResponse;
import com.uca.pncparcialfinalhotel.domain.entity.Usuario;
import com.uca.pncparcialfinalhotel.services.ReservaService;
import com.uca.pncparcialfinalhotel.shared.response.GeneralResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    @PostMapping
    @PreAuthorize("hasRole('HUESPED')")
    public ResponseEntity<GeneralResponse<ReservaResponse>> crear(
            @Valid @RequestBody CrearReservaRequest request,
            @AuthenticationPrincipal Usuario usuarioAutenticado) {
        ReservaResponse response = reservaService.crear(request, usuarioAutenticado);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GeneralResponse.of("Reserva creada exitosamente", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse<ReservaResponse>> obtenerPorId(
            @PathVariable Long id,
            @AuthenticationPrincipal Usuario usuarioAutenticado) {
        ReservaResponse response = reservaService.obtenerPorId(id, usuarioAutenticado);
        return ResponseEntity.ok(GeneralResponse.of("Reserva encontrada", response));
    }

    @GetMapping("/mis-reservas")
    @PreAuthorize("hasRole('HUESPED')")
    public ResponseEntity<GeneralResponse<List<ReservaResponse>>> obtenerMisReservas(
            @AuthenticationPrincipal Usuario usuarioAutenticado) {
        List<ReservaResponse> response = reservaService.obtenerMisReservas(usuarioAutenticado);
        return ResponseEntity.ok(GeneralResponse.of("Reservas obtenidas", response));
    }

    @GetMapping("/hotel/{hotelId}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'RECEPCIONISTA')")
    public ResponseEntity<GeneralResponse<List<ReservaResponse>>> obtenerPorHotel(
            @PathVariable Long hotelId,
            @AuthenticationPrincipal Usuario usuarioAutenticado) {
        List<ReservaResponse> response = reservaService.obtenerPorHotel(hotelId, usuarioAutenticado);
        return ResponseEntity.ok(GeneralResponse.of("Reservas del hotel obtenidas", response));
    }

    @PatchMapping("/{id}/confirmar")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'RECEPCIONISTA')")
    public ResponseEntity<GeneralResponse<ReservaResponse>> confirmar(
            @PathVariable Long id,
            @AuthenticationPrincipal Usuario usuarioAutenticado) {
        ReservaResponse response = reservaService.confirmar(id, usuarioAutenticado);
        return ResponseEntity.ok(GeneralResponse.of("Reserva confirmada exitosamente", response));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<GeneralResponse<ReservaResponse>> cancelar(
            @PathVariable Long id,
            @AuthenticationPrincipal Usuario usuarioAutenticado) {
        ReservaResponse response = reservaService.cancelar(id, usuarioAutenticado);
        return ResponseEntity.ok(GeneralResponse.of("Reserva cancelada exitosamente", response));
    }
}