package cl.kibernum.miprimerspringboot.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO DE SALIDA PARA ESTUDIANTES
 * ───────────────────────────────
 * Extiende PersonaResponseDto (hereda id, nombres, apellidos, rut, fechaNac).
 * Agrega los campos específicos del estudiante más el grado como DTO anidado.
 *
 * Ejemplo de JSON retornado:
 * {
 *   "id": 1,
 *   "nombres": "Juan",
 *   "apellido1": "Pérez",
 *   "rut": "11111111-1",
 *   "fechaNac": "2009-01-01",
 *   "gradoResponseDto": {
 *     "id": 1,
 *     "nombre": "Blanco",
 *     "kyuDan": "10° Kyu"
 *   },
 *   "fechaAscenso": "2024-03-10",
 *   "activo": true
 * }
 */
@Getter @Setter
public class EstudianteResponseDto extends PersonaResponseDto {

    /**
     * El grado del estudiante se representa como un DTO anidado (no solo el ID).
     * Así el cliente recibe toda la información del grado sin hacer una segunda consulta.
     */
    private GradoResponseDto gradoResponseDto;

    private LocalDate fechaAscenso;
    private boolean activo;
}
