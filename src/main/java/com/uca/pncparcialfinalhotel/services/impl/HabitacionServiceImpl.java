package com.uca.pncparcialfinalhotel.services.impl;

import com.uca.pncparcialfinalhotel.domain.dto.request.CrearHabitacionRequest;
import com.uca.pncparcialfinalhotel.domain.dto.response.HabitacionResponse;
import com.uca.pncparcialfinalhotel.domain.entity.Habitacion;
import com.uca.pncparcialfinalhotel.domain.entity.Hotel;
import com.uca.pncparcialfinalhotel.repositories.HabitacionRepository;
import com.uca.pncparcialfinalhotel.repositories.HotelRepository;
import com.uca.pncparcialfinalhotel.services.HabitacionService;
import com.uca.pncparcialfinalhotel.shared.exception.HotelException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HabitacionServiceImpl implements HabitacionService {

    private final HabitacionRepository habitacionRepository;
    private final HotelRepository hotelRepository;

    @Override
    public HabitacionResponse crear(CrearHabitacionRequest request) {
        Hotel hotel = hotelRepository.findById(request.hotelId())
                .orElseThrow(() -> HotelException.notFound("Hotel", request.hotelId()));

        if (habitacionRepository.existsByNumeroAndHotelId(request.numero(), request.hotelId())) {
            throw HotelException.alreadyExists(
                    "Ya existe la habitación " + request.numero() + " en este hotel"
            );
        }

        Habitacion habitacion = Habitacion.builder()
                .numero(request.numero())
                .tipo(request.tipo())
                .precioPorNoche(request.precioPorNoche())
                .disponible(true)
                .hotel(hotel)
                .build();

        return toResponse(habitacionRepository.save(habitacion));
    }

    @Override
    public HabitacionResponse obtenerPorId(Long id) {
        return habitacionRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> HotelException.notFound("Habitacion", id));
    }

    @Override
    public List<HabitacionResponse> obtenerPorHotel(Long hotelId) {
        if (!hotelRepository.existsById(hotelId)) {
            throw HotelException.notFound("Hotel", hotelId);
        }
        return habitacionRepository.findByHotelId(hotelId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<HabitacionResponse> obtenerDisponiblesPorHotel(Long hotelId) {
        if (!hotelRepository.existsById(hotelId)) {
            throw HotelException.notFound("Hotel", hotelId);
        }
        return habitacionRepository.findByHotelIdAndDisponible(hotelId, true)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public void eliminar(Long id) {
        if (!habitacionRepository.existsById(id)) {
            throw HotelException.notFound("Habitacion", id);
        }
        habitacionRepository.deleteById(id);
    }

    private HabitacionResponse toResponse(Habitacion h) {
        return new HabitacionResponse(
                h.getId(),
                h.getNumero(),
                h.getTipo(),
                h.getPrecioPorNoche(),
                h.getDisponible(),
                h.getHotel().getId(),
                h.getHotel().getNombre()
        );
    }
}