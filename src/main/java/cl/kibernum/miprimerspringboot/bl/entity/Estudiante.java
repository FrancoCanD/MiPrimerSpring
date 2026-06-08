package cl.kibernum.miprimerspringboot.bl.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * ENTIDAD: ESTUDIANTE
 * ────────────────────
 * Extiende Persona usando herencia JOINED: la tabla "estudiantes" solo almacena
 * los campos propios del alumno; los datos personales están en "personas".
 *
 * @PrimaryKeyJoinColumn(name = "persona_id") indica que la clave primaria de
 * "estudiantes" es también una FK que apunta a personas(id).
 * Así un estudiante con id=1 tiene también una fila en personas con id=1.
 *
 * Para crear o consultar un Estudiante, JPA hace internamente:
 *   SELECT p.*, e.* FROM personas p JOIN estudiantes e ON p.id = e.persona_id WHERE p.id = ?
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "estudiantes")
@PrimaryKeyJoinColumn(name = "persona_id")
public class Estudiante extends Persona {

    /**
     * Grado (cinturón) actual del alumno.
     * @ManyToOne: muchos estudiantes pueden tener el mismo grado.
     * FetchType.EAGER: carga el grado junto con el estudiante en la misma consulta.
     *   Necesario para mostrar el nombre del grado en las vistas sin LazyInitializationException.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "grado_id", referencedColumnName = "id")
    @NotNull(message = "Debe seleccionar un grado")
    private Grado grado;

    /** Fecha en que el alumno ascendió al grado actual. */
    @Column(nullable = false, name = "fecha_ascenso")
    @NotNull(message = "La fecha de ascenso es obligatoria")
    private LocalDate fechaAscenso;

    /** Fecha en que el alumno se inscribió en la academia. Puede ser null. */
    @Column(name = "fecha_inscripcion")
    private LocalDate fechaInscripcion;

    /**
     * Estado de matrícula del alumno.
     * true = activo en la academia, false = dado de baja.
     * Se usa boolean (primitivo) en vez de Boolean (objeto) porque siempre tiene valor.
     */
    @Column(nullable = false)
    private boolean activo;

    /**
     * Constructor completo manual necesario para la herencia con Lombok.
     * Lombok no puede generar @AllArgsConstructor automáticamente cuando
     * la clase padre tiene campos privados sin acceso directo.
     * Por eso se llama a super() con los campos de Persona.
     */
    public Estudiante(Integer id, String nombres, String apellido1, String apellido2,
                      LocalDate fechaNac, String rut, Grado grado,
                      LocalDate fechaAscenso, LocalDate fechaInscripcion, boolean activo) {
        super(id, nombres, apellido1, apellido2, fechaNac, rut);
        this.grado = grado;
        this.fechaAscenso = fechaAscenso;
        this.fechaInscripcion = fechaInscripcion;
        this.activo = activo;
    }
}
