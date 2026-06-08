package cl.kibernum.miprimerspringboot.bl.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * ENTIDAD ROL
 * ────────────
 * Representa un rol de seguridad que determina qué puede hacer un usuario en el sistema.
 *
 * Roles disponibles (definidos en data.sql):
 *   - ROL_ADMIN      → acceso total al sistema
 *   - ROL_INSTRUCTOR → acceso a clases, estudiantes, asistencias
 *   - ROL_ESTUDIANTE → acceso limitado (solo ver sus datos)
 *
 * IMPORTANTE: Los nombres de los roles en la BD deben coincidir EXACTAMENTE
 * con los strings usados en SecurityConfig (.hasAuthority("ROL_ADMIN")).
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre del rol. Ejemplos: "ROL_ADMIN", "ROL_INSTRUCTOR", "ROL_ESTUDIANTE".
     * unique = true garantiza que no haya roles duplicados en la BD.
     */
    @Column(nullable = false, unique = true, length = 50)
    @NotBlank(message = "Debe ingresar un nombre para el rol")
    private String nombre;

    /**
     * RELACIÓN MUCHOS A MUCHOS INVERSA: Rol ↔ Usuario
     * ──────────────────────────────────────────────────
     * mappedBy = "roles" indica que la relación ya está definida en la clase Usuario
     * (en el campo llamado "roles"). Aquí solo mantenemos la referencia inversa.
     *
     * Esta colección permite saber qué usuarios tienen este rol,
     * pero raramente se usa directamente. La dirección principal
     * de la relación es Usuario → Rol (desde Usuario se consultan los roles).
     */
    @ManyToMany(mappedBy = "roles")
    private Set<Usuario> usuarios = new HashSet<>();
}
