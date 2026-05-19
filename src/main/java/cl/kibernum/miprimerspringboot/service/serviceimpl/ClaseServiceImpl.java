package cl.kibernum.miprimerspringboot.service.serviceimpl;

import cl.kibernum.miprimerspringboot.bl.entity.Clase;
import cl.kibernum.miprimerspringboot.repository.ClaseRepository;
import cl.kibernum.miprimerspringboot.service.ClaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de los servicios de clase
 */
@Service
public class ClaseServiceImpl implements ClaseService {
    /**
     * Inyección de dependencias por anotación Autowired
     */
    @Autowired
    private ClaseRepository claseRepository;

    /**
     * Implementación de Lista todas las clases
     * @return Lista de Clases
     */
    @Override
    public List<Clase> listarClases() {
        return claseRepository.findAll();
    }

    /**
     * Implementación búsqueda de clases por id
     * @param idClase
     * @return Clase
     */
    @Override
    public Optional<Clase> clasePorId(Integer idClase) {
        return claseRepository.findById(idClase);
    }

    /**
     * Implementación de Creación o guardado de clase
     * @param clase (Objeto de tipo clase)
     * @return 1
     */
    @Override
    public Clase crearClase(Clase clase) {
        return claseRepository.save(clase);
    }

    /**
     * Implementación Eliminar clase por ID.
     * @param idClase de la clase a borrar
     */
    @Override
    public void borrarClase(Integer idClase) {
        claseRepository.deleteById(idClase);
    }
}
