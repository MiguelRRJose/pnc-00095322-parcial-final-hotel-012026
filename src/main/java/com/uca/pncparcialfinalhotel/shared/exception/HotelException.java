package com.uca.pncparcialfinalhotel.shared.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;

@Getter
public class HotelException extends RuntimeException {

    private final HttpStatus status;

    public HotelException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }


    public static HotelException notFound(String entidad, Long id) {
        return new HotelException(
                entidad + " con id " + id + " no encontrado/a",
                HttpStatus.NOT_FOUND
        );
    }

    public static HotelException alreadyExists(String mensaje) {
        return new HotelException(mensaje, HttpStatus.CONFLICT);
    }

    public static HotelException badRequest(String mensaje) {
        return new HotelException(mensaje, HttpStatus.BAD_REQUEST);
    }

    public static HotelException forbidden(String mensaje) {
        return new HotelException(mensaje, HttpStatus.FORBIDDEN);
    }

    public static HotelException unauthorized(String mensaje) {
        return new HotelException(mensaje, HttpStatus.UNAUTHORIZED);
    }
}