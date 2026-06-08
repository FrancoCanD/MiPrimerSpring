package cl.kibernum.miprimerspringboot.bl.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * ENTIDAD: ASISTENCIA
 * ────────────────────
 * Registra que un estudiante asistió a una clase, dictada por un instructor,
 * en una fecha determinada.
 *
 * RELACIONES DE ESTA ENTIDAD:
 *   Asistencia → Estudiante  (muchas asistencias pueden pertenecer a un estudiante)
 *   Asistencia → Instructor  (muchas asistencias pueden ser de un instructor)
 *   Asistencia → Clase       (muchas asistencias pueden ser del mismo tipo de clase)
 *
 * En términos de base de datos:
 *   La tabla "asistencias" tiene tres FK:
 *     - estudiante_id → estudiantes(persona_id)
 *     - instructor_id → instructores(persona_id)
 *     - clase_id      → clases(id)
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "asistencias")
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Fecha y hora exacta en que se registró la asistencia en el sistema.
     * LocalDateTime incluye fecha + hora: 2026-05-05T20:30:00
     * @DateTimeFormat indica a Spring cómo convertir el String del formulario a LocalDateTime.
     */
    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime registro;

    /**
     * Fecha en que se realizó la clase (solo la fecha, sin hora).
     * LocalDate: 2026-05-05
     */
    @Column(nullable = false, name = "fecha_clase")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaClase;

    /**
     * Estudiante que asistió a la clase.
     * @ManyToOne: muchas asistencias pueden ser del mismo estudiante.
     * FetchType.EAGER: carga el estudiante junto con la asistencia (evita LazyInitializationException en vistas).
     * @JoinColumn: nombre de la columna FK en la tabla "asistencias".
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estudiante_id", nullable = false)
    private Estudiante estudiante;

    /**
     * Instructor que dictó la clase.
     * Misma lógica que el campo estudiante.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "instructor_id", nullable = false)
    private Instructor instructor;

    /**
     * Tipo de clase a la que se asistió (Técnica, Kata, Sparring, etc.).
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clase_id", nullable = false)
    private Clase clase;
}
