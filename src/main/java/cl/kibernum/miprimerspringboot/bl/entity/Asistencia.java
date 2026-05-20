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
 * Entidad Asistencia, muestra la asistencia registrada para los estudiantes
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "asistencias")

public class Asistencia{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Fecha y hora del registro de asistencia
     */
    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime registro;

    /**
     * Fecha en que se realizó la clase
     */
    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaClase;

    /**
     * Relación con Persona (puede ser Estudiante o Profesor)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_id", referencedColumnName = "id")
    private Persona persona;

    /**
     * Relación específica con Estudiante
     * Esta columna existe en la BD pero apunta al mismo estudiante que persona
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_id", referencedColumnName = "persona_id")
    private Estudiante estudiante;

    /**
     * Relación con la Clase
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clase_id", referencedColumnName = "id")
    private Clase clase;

}