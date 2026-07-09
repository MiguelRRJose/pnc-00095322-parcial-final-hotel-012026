# PROMPTS.md — Bitácora de uso de IA

Herramienta utilizada: Claude (Anthropic)

---

## 1. Estructura del proyecto
**Prompt:** "Necesito construir una API REST en Spring Boot para un sistema de reservas de hotel con arquitectura N-Capas. Dame la estructura de paquetes completa."
**Qué generó:** Estructura con controllers, domain/entity, domain/dto, repositories, services/impl, security y shared.
**Correcciones:** Ninguna, se adaptó correctamente al estilo de trabajo del curso.

---

## 2. Entidades JPA
**Prompt:** "Generame las entidades Hotel, Habitacion, Reserva y Usuario con JPA, Lombok y relaciones correctas."
**Qué generó:** Las 4 entidades con sus relaciones @ManyToOne y @OneToMany, enums y UserDetails implementado en Usuario.
**Correcciones:** Ninguna relevante.

---

## 3. Query JPQL de solapamiento de fechas
**Prompt:** "Necesito un query en el repositorio que detecte si una habitación ya tiene una reserva en un rango de fechas dado."
**Qué generó:** Query JPQL con lógica de solapamiento usando fechaInicio < :fechaFin AND fechaFin > :fechaInicio.
**Correcciones:** Ninguna, la lógica era correcta.

---

## 4. Capa de seguridad JWT
**Prompt:** "Generame JwtService, JwtAuthFilter, UserDetailsServiceImpl y SecurityConfig para autenticación stateless con JWT en Spring Boot 4.x."
**Qué generó:** Los 4 archivos completos con generación de access token y refresh token diferenciados por claim 'type'.
**Correcciones:** Ninguna estructural.

---

## 5. Error en DaoAuthenticationProvider
**Prompt:** "DaoAuthenticationProvider está dando error al usar new DaoAuthenticationProvider(passwordEncoder()), dice que Required type es UserDetailsService."
**Qué generó la IA inicialmente:** Constructor con PasswordEncoder como argumento.
**Corrección manual:** En Spring Boot 4.x el constructor cambió y ahora recibe UserDetailsService. Se corrigió a:
new DaoAuthenticationProvider(userDetailsService) y se movió setPasswordEncoder() aparte.

---

## 6. Capa de excepciones
**Prompt:** "Necesito una capa de excepciones con una excepción personalizada, un GlobalExceptionHandler con @RestControllerAdvice y un ErrorResponse record."
**Qué generó:** HotelException con patrón factory, GlobalExceptionHandler manejando JWT, validaciones y acceso denegado.
**Correcciones:** Ninguna.

---

## 7. Regla de negocio B — Autorización por sucursal
**Prompt:** "Implementá la regla B del parcial: un Recepcionista solo puede confirmar, modificar o cancelar reservas de su propia sucursal. No basta con verificar el rol."
**Qué generó:** Método verificarAccesoRecepcionista() que compara solicitante.getHotel().getId() contra reserva.getHabitacion().getHotel().getId().
**Correcciones:** Ninguna, la lógica de comparación de atributos fue correcta.

---

## 8. Services e implementaciones
**Prompt:** "Generame los services e impl para Hotel, Habitacion, Usuario, Reserva y Auth con GeneralResponse, manejo de excepciones y mapeo a DTOs."
**Qué generó:** Interfaces y sus implementaciones completas con toResponse() privado en cada impl.
**Correcciones:** Ninguna relevante.

---

## 9. Controllers REST
**Prompt:** "Generame los controllers para Auth, Hotel, Habitacion, Usuario y Reserva usando @AuthenticationPrincipal para obtener el usuario autenticado."
**Qué generó:** 5 controllers con @PreAuthorize por rol y @AuthenticationPrincipal Usuario en los endpoints de reservas.
**Correcciones:** Ninguna.

---

## 10. Docker y GitHub Actions
**Prompt:** "Generame un Dockerfile multistage para Spring Boot con Java 21 y un docker-compose.yml que levante la API junto con PostgreSQL."
**Qué generó:** Dockerfile con builder stage y runtime stage, docker-compose con healthcheck y variables de entorno.
**Correcciones:** Se verificó que la URL de BD use el nombre del servicio 'db' en vez de 'localhost' dentro de Docker.

**Prompt:** "Generame un pipeline de GitHub Actions que compile, ejecute tests y detecte secretos expuestos con TruffleHog."
**Qué generó:** ci.yml con servicio de PostgreSQL, cache de Gradle, compilación y TruffleHog.
**Correcciones:** Ninguna.