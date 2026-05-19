package cl.kibernum.miprimerspringboot.bl.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

/**
 * Entidad Estudiante, representa a los alumnos inscritos
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "estudiantes")
@PrimaryKeyJoinColumn(name = "persona_id") // ← FK hacia la tabla personas
public class Estudiante extends Persona {

    /**
     * Grado actual del alumno
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grado_id", referencedColumnName = "id")
    private Grado grado;

    /**
     * Fecha de ascenso del alumno al grado actual
     */
    @Column(nullable = false)
    private LocalDate fechaAscenso;

    /**
     * Situación actual del alumno en la academia
     */
    @Column(nullable = false)
    private boolean activo;

    /**
     * Constructor completo
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