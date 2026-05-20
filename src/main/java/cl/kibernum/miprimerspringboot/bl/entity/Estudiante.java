package cl.kibernum.miprimerspringboot.bl.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Entidad Estudiante, representa a los alumnos inscritos
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "estudiantes")
@PrimaryKeyJoinColumn(name = "persona_id")
public class Estudiante extends Persona {

    /**
     * Grado actual del alumno
     */
    @ManyToOne(fetch = FetchType.EAGER) // Corregido: EAGER evita LazyInitializationException en la vista listar
    @JoinColumn(name = "grado_id", referencedColumnName = "id")
    @NotNull(message = "Debe seleccionar un grado")
    private Grado grado;

    /**
     * Fecha de ascenso del alumno al grado actual
     */
    @Column(nullable = false, name = "fecha_ascenso")
    @NotNull(message = "La fecha de ascenso es obligatoria")
    private LocalDate fechaAscenso;

    /**
     * Situación actual del alumno en la academia
     */
    @Column(nullable = false)
    private boolean activo;

    /**
     * Constructor completo manual para evitar conflictos con Lombok y herencia
     */
    public Estudiante(Integer id, String nombres, String apellido1, String apellido2,
                      LocalDate fechaNac, String rut, Grado grado,
                      LocalDate fechaAscenso, boolean activo) {
        super(id, nombres, apellido1, apellido2, fechaNac, rut);
        this.grado = grado;
        this.fechaAscenso = fechaAscenso;
        this.activo = activo;
    }
}
