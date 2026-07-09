package com.uca.pncparcialfinalhotel.repositories;

import com.uca.pncparcialfinalhotel.domain.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    boolean existsByNombreAndCiudad(String nombre, String ciudad);
}