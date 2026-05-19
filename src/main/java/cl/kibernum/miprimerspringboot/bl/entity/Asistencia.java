package cl.kibernum.miprimerspringboot.bl.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private Long id;
    /**
     *
     */
    @Column(nullable = false)
    private LocalDateTime registro;
    /**
     *
     */
    @Column(nullable = false)
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
    @JoinColumn(name = "clase_id", referencedColumnName = "clase")
    private Clase clase;




}
