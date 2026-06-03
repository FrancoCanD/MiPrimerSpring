package cl.kibernum.miprimerspringboot.service.serviceimpl;

import cl.kibernum.miprimerspringboot.bl.entity.Asistencia;
import cl.kibernum.miprimerspringboot.bl.entity.Estudiante;
import cl.kibernum.miprimerspringboot.dto.FichaEstudianteDto;
import cl.kibernum.miprimerspringboot.dto.request.EstudianteRequestDto;
import cl.kibernum.miprimerspringboot.dto.response.EstudianteResponseDto;
import cl.kibernum.miprimerspringboot.mapper.EstudianteMapper;
import cl.kibernum.miprimerspringboot.repository.AsistenciaRepository;
import cl.kibernum.miprimerspringboot.repository.EstudianteRepository;
import cl.kibernum.miprimerspringboot.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación de los servicios del estudiante
 */
@Service
public class EstudianteServiceImpl implements EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @Autowired
    private EstudianteMapper estudianteMapper;

    @Override
    public List<Estudiante> listarEstudiantes() {
        return estudianteRepository.findAll();
    }

    @Override
    public Optional<Estudiante> estudiantePorId(Integer id) {
        return estudianteRepository.findById(id);
    }

    @Override
    public Estudiante crearEstudiante(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    @Override
    public void borrarEstudiante(Integer id) {
        estudianteRepository.deleteById(id);
    }

    /**
     * Busca un estudiante por su RUT
     */
    @Override
    public Optional<Estudiante> buscarPorRut(String rut) {
        return estudianteRepository.findByRut(rut);
    }

    /**
     * Arma la ficha completa del estudiante
     * Calcula la edad a partir de la fecha de nacimiento
     * Obtiene sus asistencias filtrando por su ID
     */
    @Override
    public FichaEstudianteDto obtenerFicha(String rut) {
        Estudiante estudiante = estudianteRepository.findByRut(rut)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con RUT: " + rut));

        FichaEstudianteDto ficha = new FichaEstudianteDto();

        // Datos personales
        ficha.setNombres(estudiante.getNombres());
        ficha.setApellido1(estudiante.getApellido1());
        ficha.setApellido2(estudiante.getApellido2());
        ficha.setRut(estudiante.getRut());

        // Cálculo de edad
        int edad = Period.between(estudiante.getFechaNac(), LocalDate.now()).getYears();
        ficha.setEdad(edad);
        ficha.setFechaNac(estudiante.getFechaNac());

        // Datos académicos
        ficha.setGrado(estudiante.getGrado() != null ? estudiante.getGrado().getNombre() : "Sin grado");
        ficha.setFechaAscenso(estudiante.getFechaAscenso());
        ficha.setFechaInscripcion(estudiante.getFechaInscripcion());
        ficha.setActivo(estudiante.isActivo());

        // Asistencias del estudiante
        List<Asistencia> asistencias = asistenciaRepository.findAll()
                .stream()
                .filter(a -> a.getEstudiante().getId().equals(estudiante.getId()))
                .toList();
        ficha.setAsistencias(asistencias);

        return ficha;
    }

    /**
     * Lista las asistencias de un estudiante buscando por RUT
     */
    @Override
    public List<Asistencia> listarAsistenciasPorRut(String rut) {
        Estudiante estudiante = estudianteRepository.findByRut(rut)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con RUT: " + rut));

        return asistenciaRepository.findAll()
                .stream()
                .filter(a -> a.getEstudiante().getId().equals(estudiante.getId()))
                .toList();
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
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        return estudianteMapper.estudianteToEstudianteResponseDto(estudiante);
    }

    @Override
    public EstudianteResponseDto actualizarEstudianteApi(EstudianteRequestDto estudianteRequestDto, Integer id) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        estudianteMapper.updateEstudianteEntity(estudianteRequestDto, estudiante);
        estudiante = estudianteRepository.save(estudiante);
        return estudianteMapper.estudianteToEstudianteResponseDto(estudiante);
    }

    @Override
    public void borrarEstudianteApi(Integer id) {
        estudianteRepository.deleteById(id);
    }
}
