package cl.kibernum.miprimerspringboot.service.serviceimpl;

import cl.kibernum.miprimerspringboot.bl.entity.Clase;
import cl.kibernum.miprimerspringboot.dto.request.ClaseRequestDto;
import cl.kibernum.miprimerspringboot.dto.response.ClaseResponseDto;
import cl.kibernum.miprimerspringboot.mapper.ClaseMapper;
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

    @Autowired
    private ClaseMapper claseMapper;

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
     * @param id
     * @return Clase
     */
    @Override
    public Optional<Clase> clasePorId(Integer id) {
        return claseRepository.findById(id);
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
     * @param id de la clase a borrar
     */
    @Override
    public void borrarClase(Integer id) {
        claseRepository.deleteById(id);
    }

    @Override
    public List<ClaseResponseDto> listarClasesApi() {
        return claseRepository.findAll()
                .stream()
                .map(claseMapper::claseToClaseResponseDto)
                .toList();
    }

    @Override
    public ClaseResponseDto clasePorIdApi(Integer id) {
        Clase clase = claseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clase no encontrada con id: " + id));
        return claseMapper.claseToClaseResponseDto(clase);
    }

    @Override
    public ClaseResponseDto crearClaseApi(ClaseRequestDto claseRequestDto) {
        Clase clase = claseMapper.claseDtoToEntity(claseRequestDto);
        claseRepository.save(clase);
        return claseMapper.claseToClaseResponseDto(clase);
    }

    @Override
    public ClaseResponseDto actualizarClaseApi(ClaseRequestDto claseRequestDto, Integer id) {
        Clase clase = claseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clase no encontrada con id: " + id));
        claseMapper.updateClaseEntity(claseRequestDto, clase);
        clase = claseRepository.save(clase);
        return claseMapper.claseToClaseResponseDto(clase);
    }

    @Override
    public void borrarClaseApi(Integer id) {
        claseRepository.deleteById(id);
    }
}