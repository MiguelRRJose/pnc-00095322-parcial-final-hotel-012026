package com.uca.pncparcialfinalhotel.services;

import com.uca.pncparcialfinalhotel.domain.dto.request.CrearReservaRequest;
import com.uca.pncparcialfinalhotel.domain.dto.response.ReservaResponse;
import com.uca.pncparcialfinalhotel.domain.entity.Usuario;

import java.util.List;

public interface ReservaService {
    ReservaResponse crear(CrearReservaRequest request, Usuario huesped);
    ReservaResponse obtenerPorId(Long id, Usuario solicitante);
    List<ReservaResponse> obtenerMisReservas(Usuario huesped);
    List<ReservaResponse> obtenerPorHotel(Long hotelId, Usuario solicitante);
    ReservaResponse confirmar(Long id, Usuario solicitante);
    ReservaResponse cancelar(Long id, Usuario solicitante);
}