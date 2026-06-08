package cl.kibernum.miprimerspringboot.bl.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

/**
 * ENTIDAD: INSTRUCTOR
 * ────────────────────
 * Extiende Persona con herencia JOINED igual que Estudiante.
 * La tabla "instructores" almacena solo los campos propios del instructor;
 * los datos personales (nombre, RUT, etc.) están en la tabla "personas".
 *
 * Diferencia con Estudiante: el instructor tiene especialidad técnica,
 * fecha de inicio como docente, y años de experiencia.
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "instructores")
@PrimaryKeyJoinColumn(name = "persona_id")
public class Instructor extends Persona {

    /**
     * Grado (cinturón) que ostenta el instructor.
     * FetchType.EAGER: carga el grado inmediatamente para usarlo en las vistas
     * sin necesidad de una sesión JPA abierta.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "grado_id", referencedColumnName = "id")
    @NotNull(message = "El grado es obligatorio")
    private Grado grado;

    /** Especialidad técnica: "Kata", "Kumite", "Instructor General", etc. */
    @Column(nullable = false, length = 100)
    @NotBlank(message = "La especialidad es obligatoria")
    private String especialidad;

    /** Fecha en que comenzó a impartir clases en la academia. */
    @Column(name = "fecha_inicio", nullable = false)
    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    /** true = activo como instructor, false = en licencia o dado de baja. */
    @Column(nullable = false)
    private boolean activo;

    /**
     * Años de experiencia como instructor.
     * nullable = true: este dato es opcional (puede no conocerse al registrar).
     * Integer (con mayúscula) permite null; int (primitivo) no.
     */
    @Column(name = "anos_experiencia", nullable = true)
    private Integer anosExperiencia;

    /** Constructor completo con llamada a super() por la herencia con Lombok. */
    public Instructor(Integer id, String nombres, String apellido1, String apellido2,
                      LocalDate fechaNac, String rut, Grado grado, String especialidad,
                      LocalDate fechaInicio, boolean activo, Integer anosExperiencia) {
        super(id, nombres, apellido1, apellido2, fechaNac, rut);
        this.grado = grado;
        this.especialidad = especialidad;
        this.fechaInicio = fechaInicio;
        this.activo = activo;
        this.anosExperiencia = anosExperiencia;
    }
}
