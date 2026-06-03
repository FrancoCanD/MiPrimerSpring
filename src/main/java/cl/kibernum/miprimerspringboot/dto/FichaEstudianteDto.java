package cl.kibernum.miprimerspringboot.dto;

import cl.kibernum.miprimerspringboot.bl.entity.Asistencia;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO que agrupa toda la información del perfil de un estudiante
 * Se usa para la vista "Perfil del Estudiante"
 */
@Getter
@Setter
public class FichaEstudianteDto {

    // Datos personales
    private String nombres;
    private String apellido1;
    private String apellido2;
    private String rut;
    private int edad;
    private LocalDate fechaNac;

    // Datos académicos
    private String grado;
    private LocalDate fechaAscenso;
    private LocalDate fechaInscripcion;
    private boolean activo;

    // Asistencias del estudiante
    private List<Asistencia> asistencias;
}
