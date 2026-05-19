package cl.kibernum.miprimerspringboot.service.serviceimpl;

import cl.kibernum.miprimerspringboot.bl.entity.Instructor;
import cl.kibernum.miprimerspringboot.repository.InstructorRepository;
import cl.kibernum.miprimerspringboot.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de los servicios del instructor
 */
@Service
public class InstructorServiceImpl implements InstructorService {

    /**
     * Inyección de dependencias por anotación Autowired
     */
    @Autowired
    private InstructorRepository instructorRepository;

    /**
     * Implementación de Lista todos los instructores
     * @return Lista de Instructores
     */
    @Override
    public List<Instructor> listarInstructores() {
        return instructorRepository.findAll();
    }

    /**
     * Implementación búsqueda Instructor por id
     * @param id del instructor
     * @return Instructor
     */
    @Override
    public Optional<Instructor> instructorPorId(Integer id) {
        return instructorRepository.findById(id);
    }

    /**
     * Implementación de Creación o guardado de instructor
     * @param instructor (Objeto de tipo instructor)
     * @return Instructor guardado
     */
    @Override
    public Instructor crearInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    /**
     * Implementación Eliminar instructor por ID
     * @param id del instructor a borrar
     */
    @Override
    public void borrarInstructor(Integer id) {
        instructorRepository.deleteById(id);
    }
}