package cl.kibernum.miprimerspringboot.bl.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

/**
 * Entidad Instructor, representa a los instructores de la escuela
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "instructores")
@PrimaryKeyJoinColumn(name = "persona_id")
public class Instructor extends Persona {

    @ManyToOne(fetch = FetchType.EAGER) // Corregido: EAGER previene LazyInitializationException en vistas
    @JoinColumn(name = "grado_id", referencedColumnName = "id")
    @NotNull(message = "El grado es obligatorio")
    private Grado grado;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "La especialidad es obligatoria")
    private String especialidad;

    @Column(name = "fecha_inicio", nullable = false)
    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private boolean activo;

    @Column(name = "anos_experiencia", nullable = true) // Corregido: Mapeo explícito a la columna de MySQL
    private Integer anosExperiencia;

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
