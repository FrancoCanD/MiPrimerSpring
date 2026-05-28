package cl.kibernum.miprimerspringboot.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO de salida para Asistencia
 * Representa los datos que se devuelven en una respuesta Http
 */
@Getter @Setter
public class AsistenciaResponseDto {

    private Integer id;
    private LocalDateTime registro;
    private LocalDate fechaClase;
    private Integer estudianteId;
    private String estudianteNombre;
    private Integer instructorId;
    private String instructorNombre;
    private Integer claseId;
    private String claseNombre;
}
