package cl.kibernum.miprimerspringboot.service.serviceimpl;

import cl.kibernum.miprimerspringboot.bl.entity.Persona;
import cl.kibernum.miprimerspringboot.bl.entity.Usuario;
import cl.kibernum.miprimerspringboot.repository.PersonaRepository;
import cl.kibernum.miprimerspringboot.repository.UsuarioRepository;
import cl.kibernum.miprimerspringboot.service.UsuarioDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * IMPLEMENTACIÓN DEL SERVICIO DE NEGOCIO PARA USUARIOS
 * ──────────────────────────────────────────────────────
 * Esta clase implementa la interfaz UsuarioDetailsService (la de negocio, no la de Spring Security).
 * Contiene toda la lógica relacionada con la gestión de usuarios: listar, crear,
 * borrar, y vincular/desvincular personas.
 *
 * PATRÓN INTERFAZ + IMPLEMENTACIÓN:
 * En Spring Boot es una buena práctica separar QUÉ hace un servicio (interfaz)
 * de CÓMO lo hace (implementación). Esto facilita cambiar la implementación
 * sin afectar a los controladores que la usan.
 *
 * @Service registra esta clase como un componente de Spring para que pueda
 * ser inyectada con @Autowired donde se necesite.
 */
@Service
public class UsuarioServiceImpl implements UsuarioDetailsService {

    /**
     * @Autowired le pide a Spring que inyecte automáticamente el repositorio.
     * Spring lo busca entre los @Repository que creó al arrancar y lo conecta aquí.
     */
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private PersonaRepository personaRepository;

    /**
     * Retorna todos los usuarios almacenados en la base de datos.
     * findAll() es un método heredado de JpaRepository, no hay que programarlo.
     */
    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    /**
     * Busca un usuario por su ID.
     * Retorna Optional<Usuario> porque el usuario puede o no existir.
     * El controlador que llame a este método debe manejar el caso de que esté vacío.
     */
    @Override
    public Optional<Usuario> usuarioPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    /**
     * Guarda un nuevo usuario en la base de datos.
     * save() inserta si el objeto no tiene ID, o actualiza si ya lo tiene.
     */
    @Override
    public Usuario crearUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    /**
     * Elimina un usuario de la base de datos por su ID.
     */
    @Override
    public void borrarUsuario(Integer id) {
        usuarioRepository.deleteById(id);
    }

    /**
     * VINCULAR UNA PERSONA A UN USUARIO
     * ────────────────────────────────────
     * Asocia una Persona (Estudiante o Instructor) con una cuenta de Usuario.
     * Esto permite saber quién es físicamente el dueño de cada cuenta.
     *
     * Pasos:
     *   1. Buscar el usuario por su ID (lanza excepción si no existe)
     *   2. Buscar la persona por su ID (lanza excepción si no existe)
     *   3. Asignar la persona al usuario y guardar en BD
     *
     * orElseThrow() lanza una excepción si el Optional está vacío,
     * con un mensaje descriptivo que ayuda a depurar errores.
     */
    @Override
    public void vincularPersona(Integer usuarioId, Integer personaId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + usuarioId));

        Persona persona = personaRepository.findById(personaId)
                .orElseThrow(() -> new IllegalArgumentException("Persona no encontrada: " + personaId));

        // Asignamos la persona al campo @OneToOne del usuario
        usuario.setPersona(persona);

        // save() detecta que el usuario ya existe (tiene ID) y hace un UPDATE en la BD
        usuarioRepository.save(usuario);
    }

    /**
     * DESVINCULAR LA PERSONA DE UN USUARIO
     * ──────────────────────────────────────
     * Rompe el vínculo entre un usuario y su persona asignada.
     * Al asignar null, la columna persona_id queda vacía en la BD (nullable).
     */
    @Override
    public void desvincularPersona(Integer usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + usuarioId));

        // null elimina la referencia: persona_id = NULL en la base de datos
        usuario.setPersona(null);
        usuarioRepository.save(usuario);
    }
}
