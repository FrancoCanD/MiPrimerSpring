package cl.kibernum.miprimerspringboot.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO DE ENTRADA PARA ESTUDIANTES
 * ────────────────────────────────
 * Extiende PersonaRequestDto, heredando los campos comunes (nombres, apellido1, rut, etc.).
 * Agrega solo los campos específicos de un estudiante.
 *
 * Ejemplo de JSON esperado:
 * {
 *   "nombres": "Juan",
 *   "apellido1": "Pérez",
 *   "rut": "11111111-1",
 *   "fechaNac": "2009-01-01",
 *   "idGrado": 1,
 *   "fechaAscenso": "2024-03-10",
 *   "activo": true
 * }
 */
@Getter @Setter
public class EstudianteRequestDto extends PersonaRequestDto {

    /**
     * ID del grado que se le asignará al estudiante.
     * El mapper (EstudianteMapper) lo convierte al objeto Grado completo buscando en la BD.
     */
    @NotNull(message = "Debe seleccionar un grado")
    private Integer idGrado;

    /** Fecha del último ascenso de grado. */
    @NotNull(message = "La fecha de ascenso es obligatoria")
    private LocalDate fechaAscenso;

    /** Estado de matrícula: true = activo, false = inactivo. */
    @NotNull
    private boolean activo;
}
