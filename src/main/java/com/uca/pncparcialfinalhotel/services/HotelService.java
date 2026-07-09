package com.uca.pncparcialfinalhotel.services;

import com.uca.pncparcialfinalhotel.domain.dto.request.CrearHotelRequest;
import com.uca.pncparcialfinalhotel.domain.dto.response.HotelResponse;

import java.util.List;

public interface HotelService {
    HotelResponse crear(CrearHotelRequest request);
    HotelResponse obtenerPorId(Long id);
    List<HotelResponse> obtenerTodos();
    void eliminar(Long id);
}