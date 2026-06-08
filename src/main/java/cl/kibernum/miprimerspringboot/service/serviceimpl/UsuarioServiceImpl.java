package cl.kibernum.miprimerspringboot.service.serviceimpl;

import cl.kibernum.miprimerspringboot.bl.entity.Persona;
import cl.kibernum.miprimerspringboot.bl.entity.Rol;
import cl.kibernum.miprimerspringboot.bl.entity.Usuario;
import cl.kibernum.miprimerspringboot.dto.request.UsuarioCreateRequestDto;
import cl.kibernum.miprimerspringboot.dto.response.UsuarioResponseDto;
import cl.kibernum.miprimerspringboot.mapper.UsuarioMapper;
import cl.kibernum.miprimerspringboot.repository.PersonaRepository;
import cl.kibernum.miprimerspringboot.repository.RolRepository;
import cl.kibernum.miprimerspringboot.repository.UsuarioRepository;
import cl.kibernum.miprimerspringboot.service.UsuarioDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * IMPLEMENTACIÓN DEL SERVICIO DE NEGOCIO PARA USUARIOS
 * ──────────────────────────────────────────────────────
 * Implementa UsuarioDetailsService con la lógica de negocio completa.
 * El método más importante es crearUsuarioConPersonaYRol, que aplica
 * validaciones, encripta la contraseña y asigna persona + rol de una vez.
 *
 * COLABORADORES INYECTADOS:
 *   - usuarioRepository → acceso a la tabla usuarios
 *   - personaRepository → carga la persona a vincular
 *   - rolRepository     → busca el rol por nombre para asignarlo
 *   - passwordEncoder   → encripta la contraseña antes de guardarla
 *   - usuarioMapper     → convierte Usuario a UsuarioResponseDto (para la API)
 */
@Service
public class UsuarioServiceImpl implements UsuarioDetailsService {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private PersonaRepository personaRepository;
    @Autowired private RolRepository rolRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private UsuarioMapper usuarioMapper;

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> usuarioPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario crearUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public void borrarUsuario(Integer id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public void vincularPersona(Integer usuarioId, Integer personaId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + usuarioId));
        Persona persona = personaRepository.findById(personaId)
                .orElseThrow(() -> new IllegalArgumentException("Persona no encontrada: " + personaId));
        usuario.setPersona(persona);
        usuarioRepository.save(usuario);
    }

    @Override
    public void desvincularPersona(Integer usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + usuarioId));
        usuario.setPersona(null);
        usuarioRepository.save(usuario);
    }

    /**
     * CREACIÓN COMPLETA DE USUARIO CON PERSONA Y ROL
     * ────────────────────────────────────────────────
     * Este método es el núcleo de la feature de creación de usuarios.
     * Aplica en orden:
     *
     * 1. UNICIDAD DEL USERNAME:
     *    Verifica que no exista otro usuario con el mismo username.
     *    Si existe → lanza IllegalArgumentException con mensaje claro.
     *
     * 2. AUTORIZACIÓN PARA ROL_ADMIN:
     *    Si el DTO pide asignar ROL_ADMIN, verifica que el usuario autenticado
     *    tenga ROL_SUPERADMIN. De lo contrario → lanza SecurityException.
     *    Esto refuerza la regla a nivel de servicio (no solo en la vista).
     *
     * 3. PREVENCIÓN DE ROL_SUPERADMIN:
     *    ROL_SUPERADMIN nunca puede asignarse desde esta operación (ni admin ni superAdmin).
     *    Solo existe en data.sql como dato inicial del sistema.
     *
     * 4. ENCRIPTACIÓN DE CONTRASEÑA:
     *    passwordEncoder.encode() genera un hash BCrypt de la contraseña en texto plano.
     *    Nunca se guarda el texto plano.
     *
     * 5. VÍNCULO CON PERSONA (opcional):
     *    Si personaId no es null, busca la persona y la asigna.
     *    Verifica que la persona no tenga ya un usuario (restricción UNIQUE en BD).
     *
     * 6. ASIGNACIÓN DE ROL:
     *    Busca el Rol en la BD por nombre y lo agrega al Set<Rol> del usuario.
     *
     * 7. PERSISTENCIA Y RESPUESTA:
     *    Guarda el usuario completo y retorna el ResponseDto (sin contraseña).
     */
    @Override
    public UsuarioResponseDto crearUsuarioConPersonaYRol(UsuarioCreateRequestDto dto, Authentication auth) {

        // 1. Verificar unicidad del username
        if (usuarioRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con el nombre: " + dto.getUsername());
        }

        // 2. Solo ROL_SUPERADMIN puede asignar ROL_ADMIN
        if ("ROL_ADMIN".equals(dto.getRolNombre())) {
            boolean esSuperAdmin = auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROL_SUPERADMIN"));
            if (!esSuperAdmin) {
                throw new SecurityException("Solo el SuperAdmin puede asignar el rol de Administrador");
            }
        }

        // 3. ROL_SUPERADMIN nunca es asignable desde este flujo
        if ("ROL_SUPERADMIN".equals(dto.getRolNombre())) {
            throw new SecurityException("El rol SuperAdmin no puede asignarse desde este formulario");
        }

        // 4. Construir el usuario con la contraseña encriptada
        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername());
        // encode() genera algo como: $2a$10$xyz...  (hash BCrypt, irreversible)
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setEstado(true); // la cuenta queda activa por defecto

        // 5. Vincular persona si se proporcionó un ID
        if (dto.getPersonaId() != null) {
            // Verificar que la persona no tenga ya un usuario asignado
            if (usuarioRepository.existsByPersonaId(dto.getPersonaId())) {
                throw new IllegalArgumentException("Esta persona ya tiene una cuenta de usuario asignada");
            }
            Persona persona = personaRepository.findById(dto.getPersonaId())
                    .orElseThrow(() -> new IllegalArgumentException("Persona no encontrada con ID: " + dto.getPersonaId()));
            usuario.setPersona(persona);
        }

        // 6. Buscar y asignar el rol
        Rol rol = rolRepository.findByNombre(dto.getRolNombre())
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado: " + dto.getRolNombre()));
        usuario.getRoles().add(rol);

        // 7. Guardar en la BD y retornar el DTO de respuesta
        usuarioRepository.save(usuario);
        return usuarioMapper.usuarioToResponseDto(usuario);
    }

    /**
     * Lista todos los usuarios como DTOs para la API REST.
     * Usa el mapper para convertir cada Usuario a UsuarioResponseDto.
     */
    @Override
    public List<UsuarioResponseDto> listarUsuariosApi() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::usuarioToResponseDto)
                .toList();
    }
}
