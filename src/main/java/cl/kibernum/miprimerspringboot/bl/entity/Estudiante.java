package cl.kibernum.miprimerspringboot.bl.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "estudiantes")
@PrimaryKeyJoinColumn(name = "id") // Mapea la FK directamente al 'id' heredado
public class Estudiante extends Persona {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grado_id")
    private Grado grado;

    @Column(nullable = false, name = "fecha_ascenso")
    private LocalDate fechaAscenso;

    @Column(nullable = false)
    private boolean activo;

    // Tu constructor debe quedar limpio mapeando el super() con Integer
    public Estudiante(Integer id, String nombres, String apellido1, String apellido2, LocalDate fechaNac, String rut, Grado grado, LocalDate fechaAscenso, boolean activo) {
        super(id, nombres, apellido1, apellido2, fechaNac, rut);
        this.grado = grado;
        this.fechaAscenso = fechaAscenso;
        this.activo = activo;
    }
}
