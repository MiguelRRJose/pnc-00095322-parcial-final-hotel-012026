package com.uca.pncparcialfinalhotel.services.impl;

import com.uca.pncparcialfinalhotel.domain.dto.request.CrearUsuarioRequest;
import com.uca.pncparcialfinalhotel.domain.dto.response.UsuarioResponse;
import com.uca.pncparcialfinalhotel.domain.entity.Hotel;
import com.uca.pncparcialfinalhotel.domain.entity.Usuario;
import com.uca.pncparcialfinalhotel.domain.enums.Rol;
import com.uca.pncparcialfinalhotel.repositories.HotelRepository;
import com.uca.pncparcialfinalhotel.repositories.UsuarioRepository;
import com.uca.pncparcialfinalhotel.services.UsuarioService;
import com.uca.pncparcialfinalhotel.shared.exception.HotelException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final HotelRepository hotelRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UsuarioResponse crear(CrearUsuarioRequest request) {
        if (usuarioRepository.existsByUsername(request.username())) {
            throw HotelException.alreadyExists("El username ya está en uso");
        }
        if (usuarioRepository.existsByEmail(request.email())) {
            throw HotelException.alreadyExists("El email ya está registrado");
        }

        // Si es recepcionista, el hotelId es obligatorio
        Hotel hotel = null;
        if (request.rol() == Rol.RECEPCIONISTA) {
            if (request.hotelId() == null) {
                throw HotelException.badRequest("Un recepcionista debe tener un hotel asignado");
            }
            hotel = hotelRepository.findById(request.hotelId())
                    .orElseThrow(() -> HotelException.notFound("Hotel", request.hotelId()));
        }

        Usuario usuario = Usuario.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .nombre(request.nombre())
                .email(request.email())
                .rol(request.rol())
                .hotel(hotel)
                .build();

        return toResponse(usuarioRepository.save(usuario));
    }

    @Override
    public UsuarioResponse obtenerPorId(Long id) {
        return usuarioRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> HotelException.notFound("Usuario", id));
    }

    @Override
    public List<UsuarioResponse> obtenerTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw HotelException.notFound("Usuario", id);
        }
        usuarioRepository.deleteById(id);
    }

    private UsuarioResponse toResponse(Usuario u) {
        return new UsuarioResponse(
                u.getId(),
                u.getUsername(),
                u.getNombre(),
                u.getEmail(),
                u.getRol(),
                u.getHotel() != null ? u.getHotel().getId() : null,
                u.getHotel() != null ? u.getHotel().getNombre() : null
        );
    }
}