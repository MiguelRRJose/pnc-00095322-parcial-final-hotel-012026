package com.uca.pncparcialfinalhotel.controllers;

import com.uca.pncparcialfinalhotel.domain.dto.request.CrearUsuarioRequest;
import com.uca.pncparcialfinalhotel.domain.dto.response.UsuarioResponse;
import com.uca.pncparcialfinalhotel.services.UsuarioService;
import com.uca.pncparcialfinalhotel.shared.response.GeneralResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<GeneralResponse<UsuarioResponse>> crear(
            @Valid @RequestBody CrearUsuarioRequest request) {
        UsuarioResponse response = usuarioService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GeneralResponse.of("Usuario creado exitosamente", response));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<GeneralResponse<UsuarioResponse>> obtenerPorId(@PathVariable Long id) {
        UsuarioResponse response = usuarioService.obtenerPorId(id);
        return ResponseEntity.ok(GeneralResponse.of("Usuario encontrado", response));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<GeneralResponse<List<UsuarioResponse>>> obtenerTodos() {
        List<UsuarioResponse> response = usuarioService.obtenerTodos();
        return ResponseEntity.ok(GeneralResponse.of("Usuarios obtenidos", response));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<GeneralResponse<Void>> eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.ok(GeneralResponse.of("Usuario eliminado exitosamente"));
    }
}