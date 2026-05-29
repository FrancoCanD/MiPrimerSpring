package cl.kibernum.miprimerspringboot.service.serviceimpl;

import cl.kibernum.miprimerspringboot.bl.entity.Estudiante;
import cl.kibernum.miprimerspringboot.dto.request.EstudianteRequestDto;
import cl.kibernum.miprimerspringboot.dto.response.EstudianteResponseDto;
import cl.kibernum.miprimerspringboot.mapper.EstudianteMapper;
import cl.kibernum.miprimerspringboot.repository.EstudianteRepository;

import cl.kibernum.miprimerspringboot.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    @Autowired
    private EstudianteMapper estudianteMapper;

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

    @Override
    public List<EstudianteResponseDto> listarEstudiantesApi() {
        List<EstudianteResponseDto> listaEstudianteResponseDto = new ArrayList<>();
        listaEstudianteResponseDto = estudianteRepository.findAll()
                                                .stream()
                                                .map(estudianteMapper::estudianteToEstudianteResponseDto)
                                                .toList();
        return listaEstudianteResponseDto;
    }

    @Override
    public EstudianteResponseDto crearEstudianteApi(EstudianteRequestDto estudianteRequestDto) {
        Estudiante estudiante = estudianteMapper.estudianteDtoToEntity(estudianteRequestDto);
        estudianteRepository.save(estudiante);
        return estudianteMapper.estudianteToEstudianteResponseDto(estudiante);
    }

    @Override
    public EstudianteResponseDto estudiantePorIdApi(Integer id) {
        Estudiante estudiante = estudianteRepository.findById(id).orElseThrow(()-> new RuntimeException("Estudiante no encontrado"));
        return estudianteMapper.estudianteToEstudianteResponseDto(estudiante);
    }

    @Override
    public EstudianteResponseDto actualizarEstudianteApi(EstudianteRequestDto estudianteRequestDto, Integer id) {
        Estudiante estudiante = estudianteRepository.findById(id).orElseThrow(()-> new RuntimeException("Estudiante no encontrado"));
        estudianteMapper.updateEstudianteEntity(estudianteRequestDto, estudiante);
        estudiante = estudianteRepository.save(estudiante);
        return estudianteMapper.estudianteToEstudianteResponseDto(estudiante);
    }

    @Override
    public void borrarEstudianteApi(Integer id) {
        estudianteRepository.deleteById(id);
    }
}
