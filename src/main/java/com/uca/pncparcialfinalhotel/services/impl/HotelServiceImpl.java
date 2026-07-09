package com.uca.pncparcialfinalhotel.services.impl;

import com.uca.pncparcialfinalhotel.domain.dto.request.CrearHotelRequest;
import com.uca.pncparcialfinalhotel.domain.dto.response.HotelResponse;
import com.uca.pncparcialfinalhotel.domain.entity.Hotel;
import com.uca.pncparcialfinalhotel.repositories.HotelRepository;
import com.uca.pncparcialfinalhotel.services.HotelService;
import com.uca.pncparcialfinalhotel.shared.exception.HotelException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    @Override
    public HotelResponse crear(CrearHotelRequest request) {
        if (hotelRepository.existsByNombreAndCiudad(request.nombre(), request.ciudad())) {
            throw HotelException.alreadyExists(
                    "Ya existe un hotel con ese nombre en " + request.ciudad()
            );
        }

        Hotel hotel = Hotel.builder()
                .nombre(request.nombre())
                .direccion(request.direccion())
                .ciudad(request.ciudad())
                .build();

        return toResponse(hotelRepository.save(hotel));
    }

    @Override
    public HotelResponse obtenerPorId(Long id) {
        return hotelRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> HotelException.notFound("Hotel", id));
    }

    @Override
    public List<HotelResponse> obtenerTodos() {
        return hotelRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public void eliminar(Long id) {
        if (!hotelRepository.existsById(id)) {
            throw HotelException.notFound("Hotel", id);
        }
        hotelRepository.deleteById(id);
    }

    private HotelResponse toResponse(Hotel hotel) {
        return new HotelResponse(
                hotel.getId(),
                hotel.getNombre(),
                hotel.getDireccion(),
                hotel.getCiudad()
        );
    }
}