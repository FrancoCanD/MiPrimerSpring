package cl.kibernum.miprimerspringboot.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO BASE DE SALIDA PARA PERSONAS
 * ──────────────────────────────────
 * Clase abstracta con los campos comunes que se devuelven en la API
 * para EstudianteResponseDto e InstructorResponseDto.
 *
 * Al ser abstracta, no se instancia directamente.
 * Los DTOs hijos (EstudianteResponseDto, InstructorResponseDto) heredan estos campos
 * y agregan los propios de cada entidad.
 *
 * Ejemplo de JSON resultante para un Estudiante:
 * {
 *   "id": 1,
 *   "nombres": "Juan",
 *   "apellido1": "Pérez",
 *   "rut": "11111111-1",
 *   "fechaNac": "2009-01-01",
 *   "gradoResponseDto": { "nombre": "Blanco", ... },
 *   "fechaAscenso": "2024-03-10",
 *   "activo": true
 * }
 */
@Getter @Setter
public abstract class PersonaResponseDto {
    private Integer id;
    private String nombres;
    private String apellido1;
    private String apellido2;
    private LocalDate fechaNac;
    private String rut;
}
