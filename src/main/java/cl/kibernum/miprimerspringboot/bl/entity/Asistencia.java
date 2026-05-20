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
@Getter
@Setter
@Entity
@Table(name = "asistencias")
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Fecha y hora exacta en la que se realiza el marcaje en el sistema
     */
    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) // Mapea nativamente el input datetime-local
    private LocalDateTime registro;

    /**
     * Fecha del calendario correspondiente a la sesión deportiva
     */
    @Column(nullable = false, name = "fecha_clase") // Configurado con estándar snake_case
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)      // Mapea nativamente el input date estándar
    private LocalDate fechaClase;

    /**
     * Relación con el alumno que asiste a la lección de Kenpo
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Persona persona;

    /**
     * Relación con el bloque horario o tipo de clase impartida
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clase_id", referencedColumnName = "id")
    private Clase clase;
}
