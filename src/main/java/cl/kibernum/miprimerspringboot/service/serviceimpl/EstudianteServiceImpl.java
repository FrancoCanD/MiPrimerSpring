package cl.kibernum.miprimerspringboot.service.serviceimpl;

import cl.kibernum.miprimerspringboot.bl.entity.Estudiante;
import cl.kibernum.miprimerspringboot.repository.EstudianteRepository;

import cl.kibernum.miprimerspringboot.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de los servicios del estudiante
 */
@Service
public class EstudianteServiceImpl implements EstudianteService {
    /**
     * Inyección de dependencias por anotación Autowired
     */
    @Autowired
    private EstudianteRepository estudianteRepository;

    /**
     * Implementación de Lista todos los estudiantes
     * @return Lista de Estudiantes
     */
    @Override
    public List<Estudiante> listarEstudiantes() {
        return estudianteRepository.findAll();
    }

    /**
     * Implementación búsqueda Estudiante por id
     * @param id estudiante
     * @return Estudiante
     */
    @Override
    public Optional<Estudiante> estudiantePorId(Integer id) {
        return estudianteRepository.findById(id);
    }

    /**
     * Implementación de Creación o guardado de estudiante
     * @param estudiante (Objeto de tipo estudiante)
     * @return 1
     */
    @Override
    public Estudiante crearEstudiante(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    /**
     * Implementación Eliminar estudiante por ID.
     * @param id del estudiante a borrar
     */
    @Override
    public void borrarEstudiante(Integer id) {
        estudianteRepository.deleteById(id);
    }
}
