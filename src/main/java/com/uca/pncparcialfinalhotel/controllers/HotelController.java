package com.uca.pncparcialfinalhotel.controllers;

import com.uca.pncparcialfinalhotel.domain.dto.request.CrearHotelRequest;
import com.uca.pncparcialfinalhotel.domain.dto.response.HotelResponse;
import com.uca.pncparcialfinalhotel.services.HotelService;
import com.uca.pncparcialfinalhotel.shared.response.GeneralResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hoteles")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<GeneralResponse<HotelResponse>> crear(
            @Valid @RequestBody CrearHotelRequest request) {
        HotelResponse response = hotelService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GeneralResponse.of("Hotel creado exitosamente", response));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<GeneralResponse<HotelResponse>> obtenerPorId(@PathVariable Long id) {
        HotelResponse response = hotelService.obtenerPorId(id);
        return ResponseEntity.ok(GeneralResponse.of("Hotel encontrado", response));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<GeneralResponse<List<HotelResponse>>> obtenerTodos() {
        List<HotelResponse> response = hotelService.obtenerTodos();
        return ResponseEntity.ok(GeneralResponse.of("Hoteles obtenidos", response));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<GeneralResponse<Void>> eliminar(@PathVariable Long id) {
        hotelService.eliminar(id);
        return ResponseEntity.ok(GeneralResponse.of("Hotel eliminado exitosamente"));
    }
}