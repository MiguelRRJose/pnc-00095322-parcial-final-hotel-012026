package com.uca.pncparcialfinalhotel.repositories;

import com.uca.pncparcialfinalhotel.domain.entity.Habitacion;
import com.uca.pncparcialfinalhotel.domain.enums.TipoHabitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
    List<Habitacion> findByHotelId(Long hotelId);
    List<Habitacion> findByHotelIdAndDisponible(Long hotelId, Boolean disponible);
    List<Habitacion> findByHotelIdAndTipo(Long hotelId, TipoHabitacion tipo);
    boolean existsByNumeroAndHotelId(String numero, Long hotelId);
}