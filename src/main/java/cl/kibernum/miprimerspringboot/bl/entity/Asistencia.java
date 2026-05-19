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
     *
     */
    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) // Soporta el formato del input datetime-local
    private LocalDateTime registro;

    /**
     *
     */
    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) // Soporta el formato del input datetime-local
    private LocalDate fechaClase;
    /**
     *
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_id", referencedColumnName = "id")
    private Persona persona;
    /**
     *
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clase_id", referencedColumnName = "id")
    private Clase clase;




}
