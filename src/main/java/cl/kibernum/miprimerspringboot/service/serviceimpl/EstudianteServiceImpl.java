package cl.kibernum.miprimerspringboot.service.serviceimpl;

import cl.kibernum.miprimerspringboot.bl.entity.Asistencia;
import cl.kibernum.miprimerspringboot.bl.entity.Estudiante;
import cl.kibernum.miprimerspringboot.dto.FichaEstudianteDto;
import cl.kibernum.miprimerspringboot.dto.request.EstudianteRequestDto;
import cl.kibernum.miprimerspringboot.dto.response.EstudianteResponseDto;
import cl.kibernum.miprimerspringboot.bl.entity.Grado;
import cl.kibernum.miprimerspringboot.mapper.EstudianteMapper;
import cl.kibernum.miprimerspringboot.repository.AsistenciaRepository;
import cl.kibernum.miprimerspringboot.repository.EstudianteRepository;
import cl.kibernum.miprimerspringboot.repository.GradoRepository;
import cl.kibernum.miprimerspringboot.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

/**
 * IMPLEMENTACIÓN DEL SERVICIO DE ESTUDIANTES
 * ────────────────────────────────────────────
 * Contiene la lógica de negocio para gestionar alumnos.
 * Destaca el método obtenerFicha() que construye un objeto complejo
 * combinando datos de estudiante, cálculo de edad y sus asistencias.
 */
@Service
public class EstudianteServiceImpl implements EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @Autowired
    private GradoRepository gradoRepository;

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

    @Override
    public Optional<Estudiante> buscarPorRut(String rut) {
        return estudianteRepository.findByRut(rut);
    }

    /**
     * CONSTRUYE LA FICHA COMPLETA DEL ESTUDIANTE
     * ────────────────────────────────────────────
     * Este método es más complejo porque arma un objeto FichaEstudianteDto
     * con datos de múltiples fuentes: el propio estudiante y sus asistencias.
     *
     * CÁLCULO DE EDAD con Period.between():
     *   Period calcula la diferencia exacta entre dos fechas considerando años bisiestos.
     *   between(fechaNac, hoy).getYears() retorna los años completos cumplidos.
     *   Ejemplo: nacido el 2009-01-01, hoy 2026-06-08 → 17 años.
     *
     * FILTRO DE ASISTENCIAS:
     *   Como no hay un método findByEstudiante en el repositorio, se obtienen
     *   todas las asistencias y se filtran en memoria con stream().filter().
     *   (Para producción con muchos datos, sería mejor agregar una query en el repositorio.)
     */
    @Override
    public FichaEstudianteDto obtenerFicha(String rut) {
        Estudiante estudiante = estudianteRepository.findByRut(rut)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con RUT: " + rut));

        FichaEstudianteDto ficha = new FichaEstudianteDto();

        // Datos personales básicos
        ficha.setNombres(estudiante.getNombres());
        ficha.setApellido1(estudiante.getApellido1());
        ficha.setApellido2(estudiante.getApellido2());
        ficha.setRut(estudiante.getRut());

        // Calcula la edad exacta desde la fecha de nacimiento hasta hoy
        int edad = Period.between(estudiante.getFechaNac(), LocalDate.now()).getYears();
        ficha.setEdad(edad);
        ficha.setFechaNac(estudiante.getFechaNac());

        // Datos académicos
        ficha.setGrado(estudiante.getGrado() != null ? estudiante.getGrado().getNombre() : "Sin grado");
        ficha.setFechaAscenso(estudiante.getFechaAscenso());
        ficha.setFechaInscripcion(estudiante.getFechaInscripcion());
        ficha.setActivo(estudiante.isActivo());

        // Filtra las asistencias que pertenecen a este estudiante
        List<Asistencia> asistencias = asistenciaRepository.findAll()
                .stream()
                .filter(a -> a.getEstudiante().getId().equals(estudiante.getId()))
                .toList();
        ficha.setAsistencias(asistencias);

        return ficha;
    }

    /**
     * Lista las asistencias de un estudiante buscando primero por RUT.
     * Lanza RuntimeException si el RUT no existe (el controlador lo captura con try/catch).
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
    public void actualizarGradoEstudiante(Integer estudianteId, Integer gradoId, LocalDate fechaAscenso) {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado: " + estudianteId));
        Grado grado = gradoRepository.findById(gradoId)
                .orElseThrow(() -> new RuntimeException("Grado no encontrado: " + gradoId));
        estudiante.setGrado(grado);
        estudiante.setFechaAscenso(fechaAscenso);
        estudianteRepository.save(estudiante);
    }

    // ── MÉTODOS API REST ────────────────────────────────────────────────

    @Override
    public List<EstudianteResponseDto> listarEstudiantesApi() {
        return estudianteRepository.findAll()
                .stream()
                .map(estudianteMapper::estudianteToEstudianteResponseDto)
                .toList();
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
