package cl.kibernum.miprimerspringboot.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO de entrada para crear o actualizar Asistencias
 * Este objeto representa los datos que llegan desde una petición Http
 * Se usa un objeto intermedio para no exponer la entidad del modelo interno
 */
@Getter @Setter
public class AsistenciaRequestDto {

    @NotNull(message = "El registro de fecha/hora es obligatorio")
    private LocalDateTime registro;

    @NotNull(message = "La fecha de clase es obligatoria")
    private LocalDate fechaClase;

    @NotNull(message = "El estudiante es obligatorio")
    private Integer estudianteId;

    @NotNull(message = "El instructor es obligatorio")
    private Integer instructorId;

    @NotNull(message = "La clase es obligatoria")
    private Integer claseId;
}

