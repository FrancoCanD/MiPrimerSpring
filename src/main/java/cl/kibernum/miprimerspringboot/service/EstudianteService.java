package cl.kibernum.miprimerspringboot.service;

import cl.kibernum.miprimerspringboot.bl.entity.Asistencia;
import cl.kibernum.miprimerspringboot.bl.entity.Estudiante;
import cl.kibernum.miprimerspringboot.dto.FichaEstudianteDto;
import cl.kibernum.miprimerspringboot.dto.request.EstudianteRequestDto;
import cl.kibernum.miprimerspringboot.dto.response.EstudianteResponseDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * INTERFAZ DEL SERVICIO DE ESTUDIANTES
 * ──────────────────────────────────────
 * Define todas las operaciones sobre los alumnos de la academia.
 * Implementada por EstudianteServiceImpl.
 */
public interface EstudianteService {

    /** Retorna la lista completa de estudiantes. */
    List<Estudiante> listarEstudiantes();

    /** Busca un estudiante por su ID. Optional porque puede no existir. */
    Optional<Estudiante> estudiantePorId(Integer id);

    /** Crea o actualiza un estudiante en la BD. */
    Estudiante crearEstudiante(Estudiante estudiante);

    /** Elimina un estudiante por ID (también elimina su persona por la FK CASCADE). */
    void borrarEstudiante(Integer id);

    /** Busca un estudiante por su RUT. Usado en las vistas de perfil y asistencias. */
    Optional<Estudiante> buscarPorRut(String rut);

    /**
     * Construye la ficha completa del estudiante para la vista de perfil.
     * Incluye: datos personales, edad calculada, grado, historial de asistencias.
     * Retorna un FichaEstudianteDto (objeto de transferencia específico para esa vista).
     */
    FichaEstudianteDto obtenerFicha(String rut);

    /**
     * Lista todas las asistencias de un estudiante identificado por su RUT.
     * Usado en la vista "asistencias-estudiante.html".
     */
    List<Asistencia> listarAsistenciasPorRut(String rut);

    /**
     * Actualiza únicamente el grado y la fecha de ascenso/examen de un estudiante.
     * Operación exclusiva del instructor: no toca ningún otro dato personal.
     */
    void actualizarGradoEstudiante(Integer estudianteId, Integer gradoId, LocalDate fechaAscenso);

    // ── MÉTODOS API REST ────────────────────────────────────────────────

    List<EstudianteResponseDto> listarEstudiantesApi();
    EstudianteResponseDto crearEstudianteApi(EstudianteRequestDto estudianteRequestDto);
    EstudianteResponseDto estudiantePorIdApi(Integer id);
    EstudianteResponseDto actualizarEstudianteApi(EstudianteRequestDto estudianteRequestDto, Integer id);
    void borrarEstudianteApi(Integer id);
}
