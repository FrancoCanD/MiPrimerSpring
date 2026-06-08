package cl.kibernum.miprimerspringboot.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO DE ENTRADA PARA ASISTENCIAS
 * ────────────────────────────────
 * Datos que debe enviar el cliente para registrar o actualizar una asistencia.
 * Las relaciones se envían como IDs (no como objetos completos).
 * El AsistenciaMapper los convierte a las entidades correspondientes.
 *
 * Ejemplo de JSON esperado:
 * {
 *   "registro": "2026-05-05T20:30:00",
 *   "fechaClase": "2026-05-05",
 *   "estudianteId": 1,
 *   "instructorId": 3,
 *   "claseId": 1
 * }
 */
@Getter @Setter
public class AsistenciaRequestDto {

    /** Fecha y hora del registro en sistema. Formato ISO: "2026-05-05T20:30:00" */
    @NotNull(message = "El registro de fecha/hora es obligatorio")
    private LocalDateTime registro;

    /** Fecha de la clase. Formato ISO: "2026-05-05" */
    @NotNull(message = "La fecha de clase es obligatoria")
    private LocalDate fechaClase;

    /** ID del estudiante que asistió. El mapper carga el objeto Estudiante completo. */
    @NotNull(message = "El estudiante es obligatorio")
    private Integer estudianteId;

    /** ID del instructor que dictó la clase. */
    @NotNull(message = "El instructor es obligatorio")
    private Integer instructorId;

    /** ID del tipo de clase. */
    @NotNull(message = "La clase es obligatoria")
    private Integer claseId;
}
