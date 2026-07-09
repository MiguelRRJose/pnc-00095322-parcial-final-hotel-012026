package com.uca.pncparcialfinalhotel.services;

import com.uca.pncparcialfinalhotel.domain.dto.request.CrearHabitacionRequest;
import com.uca.pncparcialfinalhotel.domain.dto.response.HabitacionResponse;

import java.util.List;

public interface HabitacionService {
    HabitacionResponse crear(CrearHabitacionRequest request);
    HabitacionResponse obtenerPorId(Long id);
    List<HabitacionResponse> obtenerPorHotel(Long hotelId);
    List<HabitacionResponse> obtenerDisponiblesPorHotel(Long hotelId);
    void eliminar(Long id);
}