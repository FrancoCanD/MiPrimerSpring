package cl.kibernum.miprimerspringboot.bl.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

/**
 * Entidad Instructor, representa a los instructores de la escuela
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "instructores")
@PrimaryKeyJoinColumn(name = "persona_id") // ← FK hacia la tabla personas
public class Instructor extends Persona {

    /**
     * Grado del instructor
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grado_id", referencedColumnName = "id")
    private Grado grado;

    /**
     * Especialidad del instructor
     * Ejemplo: Kata, Kumite, Defensa Personal
     */
    @Column(nullable = false)
    private String especialidad;

    /**
     * Fecha de inicio como instructor
     */
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    /**
     * Estado del instructor (activo/inactivo)
     */
    @Column(nullable = false)
    private boolean activo;

    /**
     * Años de experiencia
     */
    @Column(nullable = true)
    private Integer anosExperiencia;

    /**
     * Constructor completo
     */
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