package cl.kibernum.miprimerspringboot.service.serviceimpl;

import cl.kibernum.miprimerspringboot.bl.entity.Grado;
import cl.kibernum.miprimerspringboot.repository.GradoRepository;
import cl.kibernum.miprimerspringboot.service.GradoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de los servicios del grado
 */
@Service
public class GradoServiceImpl implements GradoService {
    /**
     * Inyección de dependencias por anotación Autowired
     */
    @Autowired
    private GradoRepository gradoRepository;

    /**
     * Implementación de Lista todos los grados
     * @return Lista de Grados
     */
    @Override
    public List<Grado> listarGrados() {
        return gradoRepository.findAll();
    }

    /**
     * Implementación búsqueda grado por id
     * @param id grado
     * @return Grado
     */
    @Override
    public Optional<Grado> gradoPorId(Integer id) {
        return gradoRepository.findById(id);
    }

    /**
     * Implementación de Creación o guardado de grado
     * @param grado (Objeto de tipo grado)
     * @return 1
     */
    @Override
    public Grado crearGrado(Grado grado) {
        return gradoRepository.save(grado);
    }

    /**
     * Implementación Eliminar grado por ID.
     * @param id del grado a borrar
     */
    @Override
    public void borrarGrado(Integer id) {
        gradoRepository.deleteById(id);
    }
}
