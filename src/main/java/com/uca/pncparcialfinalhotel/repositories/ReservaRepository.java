package com.uca.pncparcialfinalhotel.repositories;

import com.uca.pncparcialfinalhotel.domain.entity.Reserva;
import com.uca.pncparcialfinalhotel.domain.enums.EstadoReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    // Reservas de un huésped
    List<Reserva> findByHuespedId(Long huespedId);

    // Reservas de una habitación
    List<Reserva> findByHabitacionId(Long habitacionId);

    // Reservas por hotel (para recepcionista — regla B)
    List<Reserva> findByHabitacionHotelId(Long hotelId);

    // Reservas por hotel y estado
    List<Reserva> findByHabitacionHotelIdAndEstado(Long hotelId, EstadoReserva estado);

    // Verificar solapamiento de fechas para una habitación
    //Explicacion de palabras propias xd: Esta parte se encarga para ver que habitacion se posiciona encima de la otra en cuanto a la reserva principal
    @Query("""
        SELECT COUNT(r) > 0 FROM Reserva r
        WHERE r.habitacion.id = :habitacionId
        AND r.estado != 'CANCELADA'
        AND r.fechaInicio < :fechaFin
        AND r.fechaFin > :fechaInicio
    """)
    boolean existeSolapamiento(
            @Param("habitacionId") Long habitacionId,
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin
    );
}