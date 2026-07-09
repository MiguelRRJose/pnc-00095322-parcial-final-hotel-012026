package com.uca.pncparcialfinalhotel.services.impl;

import com.uca.pncparcialfinalhotel.domain.dto.request.CrearReservaRequest;
import com.uca.pncparcialfinalhotel.domain.dto.response.ReservaResponse;
import com.uca.pncparcialfinalhotel.domain.entity.Habitacion;
import com.uca.pncparcialfinalhotel.domain.entity.Reserva;
import com.uca.pncparcialfinalhotel.domain.entity.Usuario;
import com.uca.pncparcialfinalhotel.domain.enums.EstadoReserva;
import com.uca.pncparcialfinalhotel.domain.enums.Rol;
import com.uca.pncparcialfinalhotel.repositories.HabitacionRepository;
import com.uca.pncparcialfinalhotel.repositories.ReservaRepository;
import com.uca.pncparcialfinalhotel.services.ReservaService;
import com.uca.pncparcialfinalhotel.shared.exception.HotelException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final HabitacionRepository habitacionRepository;

    @Override
    public ReservaResponse crear(CrearReservaRequest request, Usuario huesped) {
        // Solo huéspedes pueden crear reservas
        if (huesped.getRol() != Rol.HUESPED) {
            throw HotelException.forbidden("Solo los huéspedes pueden crear reservas");
        }

        if (!request.fechaFin().isAfter(request.fechaInicio())) {
            throw HotelException.badRequest("La fecha de fin debe ser posterior a la de inicio");
        }

        Habitacion habitacion = habitacionRepository.findById(request.habitacionId())
                .orElseThrow(() -> HotelException.notFound("Habitacion", request.habitacionId()));

        if (!habitacion.getDisponible()) {
            throw HotelException.badRequest("La habitación no está disponible");
        }

        if (reservaRepository.existeSolapamiento(
                request.habitacionId(),
                request.fechaInicio(),
                request.fechaFin())) {
            throw HotelException.badRequest("La habitación ya tiene una reserva en ese rango de fechas");
        }

        Reserva reserva = Reserva.builder()
                .fechaInicio(request.fechaInicio())
                .fechaFin(request.fechaFin())
                .estado(EstadoReserva.PENDIENTE)
                .huesped(huesped)
                .habitacion(habitacion)
                .build();

        return toResponse(reservaRepository.save(reserva));
    }

    @Override
    public ReservaResponse obtenerPorId(Long id, Usuario solicitante) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> HotelException.notFound("Reserva", id));

        verificarAcceso(reserva, solicitante);
        return toResponse(reserva);
    }

    @Override
    public List<ReservaResponse> obtenerMisReservas(Usuario huesped) {
        return reservaRepository.findByHuespedId(huesped.getId())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<ReservaResponse> obtenerPorHotel(Long hotelId, Usuario solicitante) {
        // ── REGLA B
        // Un recepcionista solo puede ver reservas de su propio hotel
        if (solicitante.getRol() == Rol.RECEPCIONISTA) {
            if (!solicitante.getHotel().getId().equals(hotelId)) {
                throw HotelException.forbidden(
                        "Solo podés ver reservas de tu propia sucursal"
                );
            }
        }

        return reservaRepository.findByHabitacionHotelId(hotelId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public ReservaResponse confirmar(Long id, Usuario solicitante) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> HotelException.notFound("Reserva", id));

        // ── REGLA B
        verificarAccesoRecepcionista(reserva, solicitante);

        if (reserva.getEstado() != EstadoReserva.PENDIENTE) {
            throw HotelException.badRequest("Solo se pueden confirmar reservas en estado PENDIENTE");
        }

        reserva.setEstado(EstadoReserva.CONFIRMADA);
        return toResponse(reservaRepository.save(reserva));
    }

    @Override
    public ReservaResponse cancelar(Long id, Usuario solicitante) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> HotelException.notFound("Reserva", id));


        if (solicitante.getRol() == Rol.HUESPED) {
            if (!reserva.getHuesped().getId().equals(solicitante.getId())) {
                throw HotelException.forbidden("Solo podés cancelar tus propias reservas");
            }
        } else {
            verificarAccesoRecepcionista(reserva, solicitante);
        }
        if (reserva.getEstado() == EstadoReserva.CANCELADA) {
            throw HotelException.badRequest("La reserva ya está cancelada");
        }

        reserva.setEstado(EstadoReserva.CANCELADA);
        return toResponse(reservaRepository.save(reserva));
    }

    // ── Helpers privados

    /**
     * Verifica que el solicitante pueda ver una reserva específica.
     * - ADMINISTRADOR: acceso total
     * - RECEPCIONISTA: solo su sucursal
     * - HUESPED: solo sus propias reservas
     */
    private void verificarAcceso(Reserva reserva, Usuario solicitante) {
        switch (solicitante.getRol()) {
            case ADMINISTRADOR -> { /* acceso total */ }
            case RECEPCIONISTA -> verificarAccesoRecepcionista(reserva, solicitante);
            case HUESPED -> {
                if (!reserva.getHuesped().getId().equals(solicitante.getId())) {
                    throw HotelException.forbidden("No tenés acceso a esta reserva");
                }
            }
        }
    }

    /**
     * REGLA B — Núcleo de la autorización por atributo:
     * Compara el hotel del recepcionista autenticado contra el hotel
     * de la habitación de la reserva. No basta con verificar el rol
     */
    private void verificarAccesoRecepcionista(Reserva reserva, Usuario solicitante) {
        if (solicitante.getRol() == Rol.RECEPCIONISTA) {
            Long hotelReserva = reserva.getHabitacion().getHotel().getId();
            Long hotelRecepcionista = solicitante.getHotel().getId();

            if (!hotelReserva.equals(hotelRecepcionista)) {
                throw HotelException.forbidden(
                        "No tenés permisos para operar reservas de otra sucursal"
                );
            }
        }
    }

    private ReservaResponse toResponse(Reserva r) {
        return new ReservaResponse(
                r.getId(),
                r.getFechaInicio(),
                r.getFechaFin(),
                r.getEstado(),
                r.getHuesped().getId(),
                r.getHuesped().getNombre(),
                r.getHabitacion().getId(),
                r.getHabitacion().getNumero(),
                r.getHabitacion().getHotel().getId(),
                r.getHabitacion().getHotel().getNombre()
        );
    }
}