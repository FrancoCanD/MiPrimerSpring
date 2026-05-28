package cl.kibernum.miprimerspringboot.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO de salida para Instructor
 * Representa los datos que se devuelven en una respuesta Http
 */
@Getter @Setter
public class InstructorResponseDto {

    private Integer id;
    private String nombres;
    private String apellido1;
    private String apellido2;
    private LocalDate fechaNac;
    private String rut;
    private Integer gradoId;
    private String gradoNombre;
    private String especialidad;
    private LocalDate fechaInicio;
    private boolean activo;
    private Integer anosExperiencia;
}

