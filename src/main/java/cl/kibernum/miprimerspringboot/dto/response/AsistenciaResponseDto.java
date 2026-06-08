package cl.kibernum.miprimerspringboot.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO DE SALIDA PARA ASISTENCIAS
 * ───────────────────────────────
 * Datos que la API retorna al consultar una asistencia.
 * Las relaciones se "aplanan": en vez de retornar objetos anidados,
 * se retornan el ID y el nombre de cada entidad relacionada.
 *
 * Ejemplo de JSON retornado:
 * {
 *   "id": 1,
 *   "registro": "2026-05-05T20:30:00",
 *   "fechaClase": "2026-05-05",
 *   "estudianteId": 1,
 *   "estudianteNombre": "Juan Pérez",
 *   "instructorId": 3,
 *   "instructorNombre": "Carlos Muñoz",
 *   "claseId": 1,
 *   "claseNombre": "Técnica"
 * }
 */
@Getter @Setter
public class AsistenciaResponseDto {

    private Integer id;
    private LocalDateTime registro;
    private LocalDate fechaClase;
    /** ID del estudiante (para referencias entre sistemas). */
    private Integer estudianteId;
    /** Nombre completo del estudiante (para mostrar directamente). */
    private String estudianteNombre;
    private Integer instructorId;
    private String instructorNombre;
    private Integer claseId;
    private String claseNombre;
}
