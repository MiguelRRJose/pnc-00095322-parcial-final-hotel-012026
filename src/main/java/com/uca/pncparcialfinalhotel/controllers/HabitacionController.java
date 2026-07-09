package com.uca.pncparcialfinalhotel.controllers;

import com.uca.pncparcialfinalhotel.domain.dto.request.CrearHabitacionRequest;
import com.uca.pncparcialfinalhotel.domain.dto.response.HabitacionResponse;
import com.uca.pncparcialfinalhotel.services.HabitacionService;
import com.uca.pncparcialfinalhotel.shared.response.GeneralResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habitaciones")
@RequiredArgsConstructor
public class HabitacionController {

    private final HabitacionService habitacionService;

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<GeneralResponse<HabitacionResponse>> crear(
            @Valid @RequestBody CrearHabitacionRequest request) {
        HabitacionResponse response = habitacionService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GeneralResponse.of("Habitación creada exitosamente", response));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'RECEPCIONISTA')")
    public ResponseEntity<GeneralResponse<HabitacionResponse>> obtenerPorId(@PathVariable Long id) {
        HabitacionResponse response = habitacionService.obtenerPorId(id);
        return ResponseEntity.ok(GeneralResponse.of("Habitación encontrada", response));
    }

    @GetMapping("/hotel/{hotelId}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'RECEPCIONISTA')")
    public ResponseEntity<GeneralResponse<List<HabitacionResponse>>> obtenerPorHotel(
            @PathVariable Long hotelId) {
        List<HabitacionResponse> response = habitacionService.obtenerPorHotel(hotelId);
        return ResponseEntity.ok(GeneralResponse.of("Habitaciones obtenidas", response));
    }

    @GetMapping("/hotel/{hotelId}/disponibles")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'RECEPCIONISTA')")
    public ResponseEntity<GeneralResponse<List<HabitacionResponse>>> obtenerDisponibles(
            @PathVariable Long hotelId) {
        List<HabitacionResponse> response = habitacionService.obtenerDisponiblesPorHotel(hotelId);
        return ResponseEntity.ok(GeneralResponse.of("Habitaciones disponibles obtenidas", response));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<GeneralResponse<Void>> eliminar(@PathVariable Long id) {
        habitacionService.eliminar(id);
        return ResponseEntity.ok(GeneralResponse.of("Habitación eliminada exitosamente"));
    }
}