package cl.kibernum.miprimerspringboot.bl.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * ENTIDAD USUARIO
 * ────────────────
 * Representa una cuenta de acceso al sistema (nombre de usuario + contraseña + rol).
 * NO es lo mismo que una Persona: una Persona es un individuo real (con RUT, nombre, etc.),
 * mientras que un Usuario es una credencial de autenticación.
 *
 * Un Usuario puede estar vinculado opcionalmente a una Persona (Estudiante o Instructor).
 *
 * ANOTACIONES DE LOMBOK (generan código automáticamente en tiempo de compilación):
 *   @Getter       → genera todos los métodos get (getNombre(), getPassword(), etc.)
 *   @Setter       → genera todos los métodos set
 *   @NoArgsConstructor → genera constructor vacío: new Usuario()
 *   @AllArgsConstructor → genera constructor con todos los campos
 *
 * ANOTACIONES DE JPA:
 *   @Entity  → le dice a JPA que esta clase representa una tabla en la BD
 *   @Table   → especifica el nombre exacto de la tabla en MySQL
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "usuarios")
public class Usuario {

    /**
     * Clave primaria de la tabla.
     * @GeneratedValue(IDENTITY) significa que MySQL asigna el ID automáticamente
     * con AUTO_INCREMENT (1, 2, 3, ...).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre de usuario para iniciar sesión.
     * unique = true → no puede haber dos usuarios con el mismo username en la BD.
     * @NotBlank (de Bean Validation) impide que se guarde vacío desde formularios.
     */
    @Column(nullable = false, unique = true, length = 100)
    @NotBlank(message = "Debe ingresar un nombre de usuario")
    private String username;

    /**
     * Contraseña del usuario almacenada como hash BCrypt.
     * NUNCA se guarda la contraseña en texto plano por seguridad.
     * Ejemplo de hash: $2a$10$Nfr3A60qbfg...
     */
    @Column(nullable = false)
    @NotBlank(message = "Debe ingresar una contraseña")
    private String password;

    /**
     * Estado de la cuenta: true = activa, false = deshabilitada.
     * Se usa en UsuarioDetailsService para bloquear el login de cuentas inactivas.
     */
    @Column(nullable = false)
    @NotNull(message = "Debe seleccionar el estado del usuario")
    private Boolean estado = true;

    /**
     * RELACIÓN MUCHOS A MUCHOS: Usuario ↔ Rol
     * ──────────────────────────────────────────
     * Un usuario puede tener varios roles (ROL_ADMIN, ROL_INSTRUCTOR, etc.)
     * y un rol puede estar asignado a varios usuarios.
     *
     * @JoinTable especifica la tabla intermedia "usuario_rol" que gestiona esta relación.
     *   - joinColumns: columna que apunta a esta entidad (usuario_id)
     *   - inverseJoinColumns: columna que apunta a la otra entidad (rol_id)
     *
     * FetchType.EAGER: cuando se carga un Usuario, se cargan INMEDIATAMENTE sus roles.
     * Necesario porque Spring Security necesita los roles al autenticar.
     *
     * HashSet evita roles duplicados.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuario_rol",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private Set<Rol> roles = new HashSet<>();

    /**
     * RELACIÓN UNO A UNO: Usuario → Persona
     * ───────────────────────────────────────
     * Cada cuenta de usuario puede estar vinculada a UNA persona (Estudiante o Instructor).
     * La columna "persona_id" en la tabla "usuarios" guarda el vínculo.
     *
     * optional = true  → un usuario puede existir sin persona vinculada (ej: cuenta admin).
     * FetchType.EAGER  → carga la persona junto con el usuario en la misma consulta.
     *                    Necesario para evitar LazyInitializationException en las vistas
     *                    (cuando Thymeleaf intenta acceder a usuario.persona fuera de la transacción).
     * unique = true    → una persona solo puede estar vinculada a un usuario.
     */
    @OneToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "persona_id", unique = true)
    private Persona persona;
}
