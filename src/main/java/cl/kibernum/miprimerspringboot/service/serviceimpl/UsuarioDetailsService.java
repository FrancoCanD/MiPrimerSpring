package cl.kibernum.miprimerspringboot.service.serviceimpl;

import cl.kibernum.miprimerspringboot.bl.entity.Rol;
import cl.kibernum.miprimerspringboot.bl.entity.Usuario;
import cl.kibernum.miprimerspringboot.repository.UsuarioRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * SERVICIO DE CARGA DE USUARIO PARA SPRING SECURITY
 * ───────────────────────────────────────────────────
 * Spring Security no sabe cómo leer usuarios desde nuestra base de datos.
 * Esta clase le "enseña" cómo hacerlo implementando la interfaz UserDetailsService.
 *
 * La interfaz exige implementar un único método: loadUserByUsername().
 * Spring Security lo llama automáticamente cada vez que alguien intenta iniciar sesión.
 *
 * IMPORTANTE: Esta clase es diferente a UsuarioServiceImpl.
 *   - Esta clase  → es para autenticación (Spring Security la usa internamente)
 *   - UsuarioServiceImpl → es para operaciones de negocio (CRUD, vincular personas, etc.)
 *
 * @Service le dice a Spring que cree un objeto de esta clase y lo gestione.
 */
@Service
public class UsuarioDetailsService implements UserDetailsService {

    // Spring inyecta automáticamente el repositorio gracias al constructor
    private final UsuarioRepository usuarioRepository;

    public UsuarioDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * MÉTODO PRINCIPAL: CARGAR USUARIO POR NOMBRE DE USUARIO
     * ────────────────────────────────────────────────────────
     * Spring Security llama a este método cuando alguien envía el formulario de login.
     * Recibe el username que escribió el usuario y debe devolver un objeto UserDetails
     * con toda la información necesaria para verificar la contraseña y los permisos.
     *
     * @param username el nombre de usuario ingresado en el formulario
     * @return UserDetails objeto estándar de Spring Security con credenciales y roles
     * @throws UsernameNotFoundException si el usuario no existe en la BD
     */
    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {

        // Buscamos el usuario en la base de datos por su username.
        // findByUsername devuelve un Optional: puede tener un valor o estar vacío.
        // orElseThrow lanza una excepción si no se encuentra, lo que hace que
        // Spring Security muestre el mensaje de "credenciales incorrectas".
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Convertimos los objetos Rol a un arreglo de Strings con los nombres.
        // Spring Security necesita los roles como Strings simples (ej: "ROL_ADMIN").
        // stream() recorre la colección, map() transforma cada Rol a su nombre,
        // toArray() convierte el resultado a un arreglo de String[].
        String[] authorities = usuario.getRoles()
                .stream()
                .map(Rol::getNombre)
                .toArray(String[]::new);

        // Construimos y retornamos el objeto UserDetails que Spring Security entiende.
        // User.builder() es un constructor fluido (encadenado) de Spring Security.
        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())   // Ya viene hasheado con BCrypt desde la BD
                .authorities(authorities)           // Los roles del usuario (ej: ROL_ADMIN)
                // disabled() recibe true si la cuenta está DESHABILITADA.
                // Como estado=true significa "activo", negamos el valor:
                //   estado=true  → disabled=false → cuenta habilitada ✓
                //   estado=false → disabled=true  → cuenta deshabilitada ✗
                .disabled(!Boolean.TRUE.equals(usuario.getEstado()))
                .build();
    }

    public Usuario encontrarPorUsername(Authentication auth) {
        Usuario usuario = usuarioRepository.findByUsername(auth.getName()).orElseThrow();
        return usuario;
    }
}
