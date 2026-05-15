package cl.kibernum.miprimerspringboot.bl.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class Estudiante {
    /**
     * Identificador del alumno
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * ID del Grado actual del alumno
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

}
