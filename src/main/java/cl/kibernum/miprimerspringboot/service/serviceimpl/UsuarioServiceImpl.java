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

@Service
public class UsuarioServiceImpl implements UsuarioDetailsService {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private PersonaRepository personaRepository;

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
}
