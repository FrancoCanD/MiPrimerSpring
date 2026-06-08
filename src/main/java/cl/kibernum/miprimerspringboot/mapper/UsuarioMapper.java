package cl.kibernum.miprimerspringboot.mapper;

import cl.kibernum.miprimerspringboot.bl.entity.Rol;
import cl.kibernum.miprimerspringboot.bl.entity.Usuario;
import cl.kibernum.miprimerspringboot.dto.response.UsuarioResponseDto;
import org.springframework.stereotype.Component;

/**
 * MAPPER DE USUARIO
 * ──────────────────
 * Convierte la entidad Usuario a UsuarioResponseDto para la API REST.
 *
 * NO necesita método "DtoToEntity" porque la creación de usuarios se hace
 * directamente en el servicio (UsuarioServiceImpl.crearUsuarioConPersonaYRol)
 * con lógica de negocio adicional (encoding de contraseña, búsqueda de rol, etc.).
 * Un simple campo-a-campo no sería suficiente para ese caso.
 */
@Component
public class UsuarioMapper {

    /**
     * Convierte un Usuario a UsuarioResponseDto.
     * La contraseña NUNCA se incluye en el DTO de respuesta.
     * La persona se aplana en dos campos: personaId y personaNombreCompleto.
     * Los roles se convierten de Set<Rol> a List<String> con los nombres.
     */
    public UsuarioResponseDto usuarioToResponseDto(Usuario usuario) {
        UsuarioResponseDto dto = new UsuarioResponseDto();
        dto.setId(usuario.getId());
        dto.setUsername(usuario.getUsername());
        dto.setEstado(usuario.getEstado());

        // Aplanamos la relación @OneToOne: extraemos solo id y nombre de la persona
        if (usuario.getPersona() != null) {
            dto.setPersonaId(usuario.getPersona().getId());
            dto.setPersonaNombreCompleto(
                    usuario.getPersona().getNombres() + " " + usuario.getPersona().getApellido1()
            );
        }

        // Convertimos Set<Rol> a List<String> con los nombres de los roles
        dto.setRoles(
                usuario.getRoles().stream()
                        .map(Rol::getNombre)
                        .toList()
        );

        return dto;
    }
}
