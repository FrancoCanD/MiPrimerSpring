package cl.kibernum.miprimerspringboot.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO DE SALIDA PARA INSTRUCTORES
 * ────────────────────────────────
 * Contiene todos los datos de un instructor que se retornan en la API.
 * A diferencia de EstudianteResponseDto, no extiende PersonaResponseDto
 * porque InstructorRequestDto tampoco extiende PersonaRequestDto.
 *
 * El grado se "aplana" en dos campos separados (gradoId y gradoNombre)
 * en lugar de un DTO anidado como en EstudianteResponseDto.
 * Ambos enfoques son válidos; este es más simple para clientes que solo necesitan el nombre.
 */
@Getter @Setter
public class InstructorResponseDto {

    private Integer id;
    private String nombres;
    private String apellido1;
    private String apellido2;
    private LocalDate fechaNac;
    private String rut;
    /** ID del grado del instructor (para referencias). */
    private Integer gradoId;
    /** Nombre del grado (ej: "Negro") para mostrarlo directamente. */
    private String gradoNombre;
    private String especialidad;
    private LocalDate fechaInicio;
    private boolean activo;
    private Integer anosExperiencia;
}
