package com.uca.pncparcialfinalhotel.services;

import com.uca.pncparcialfinalhotel.domain.dto.request.CrearUsuarioRequest;
import com.uca.pncparcialfinalhotel.domain.dto.response.UsuarioResponse;

import java.util.List;

public interface UsuarioService {
    UsuarioResponse crear(CrearUsuarioRequest request);
    UsuarioResponse obtenerPorId(Long id);
    List<UsuarioResponse> obtenerTodos();
    void eliminar(Long id);
}