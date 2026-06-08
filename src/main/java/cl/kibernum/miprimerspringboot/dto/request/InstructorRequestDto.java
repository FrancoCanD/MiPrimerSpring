package cl.kibernum.miprimerspringboot.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO DE ENTRADA PARA INSTRUCTORES
 * ─────────────────────────────────
 * A diferencia de EstudianteRequestDto, InstructorRequestDto NO extiende PersonaRequestDto.
 * Declara los campos personales directamente en esta clase (decisión de diseño del equipo).
 *
 * Contiene todos los datos necesarios para crear o actualizar un instructor.
 */
@Getter @Setter
public class InstructorRequestDto {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombres;

    @NotBlank(message = "El primer apellido es obligatorio")
    private String apellido1;

    private String apellido2; // Opcional

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNac;

    @NotBlank(message = "El RUT es obligatorio")
    private String rut;

    /**
     * ID del grado del instructor.
     * El InstructorMapper lo convierte al objeto Grado completo buscando en GradoRepository.
     */
    @NotNull(message = "El grado es obligatorio")
    private Integer gradoId;

    @NotBlank(message = "La especialidad es obligatoria")
    private String especialidad;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    private boolean activo;

    private Integer anosExperiencia; // Opcional
}
