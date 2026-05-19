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
public class Instructor extends Persona {

/**
 * ID del Grado del instrutor
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
@Column(nullable = false)
private LocalDate fecha_inicio;

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
        this.fecha_inicio = fechaInicio;
        this.activo = activo;
        this.anosExperiencia = anosExperiencia;
    }
}

