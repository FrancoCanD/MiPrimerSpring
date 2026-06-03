package cl.kibernum.miprimerspringboot.service;

import cl.kibernum.miprimerspringboot.bl.entity.Asistencia;
import cl.kibernum.miprimerspringboot.bl.entity.Estudiante;
import cl.kibernum.miprimerspringboot.dto.FichaEstudianteDto;
import cl.kibernum.miprimerspringboot.dto.request.EstudianteRequestDto;
import cl.kibernum.miprimerspringboot.dto.response.EstudianteResponseDto;

import java.util.List;
import java.util.Optional;

/**
 * Interface del servicio de estudiante
 * contiene los metodos abstractos del Estudiante
 */
public interface EstudianteService {

    /**
     * Lista de Estudiantes de Existentes en la BD
     * @return Lista de Estudiantes de la BD
     */
    List<Estudiante> listarEstudiantes();

    /**
     * Buscar al estudiante por id
     * @param id del estudiante
     * @return el estudiante según id
     */
    Optional<Estudiante> estudiantePorId(Integer id);

    /**
     * Crea un estudiante nuevo
     * @param estudiante (objeto de tipo Estudiante)
     * @return Estudiante guardado
     */
    Estudiante crearEstudiante(Estudiante estudiante);

    /**
     * Borra un estudiante según ID
     * @param id del estudiante a eliminar
     */
    void borrarEstudiante(Integer id);

    /**
     * Busca un estudiante por su RUT
     * @param rut del estudiante
     * @return Optional con el estudiante encontrado
     */
    Optional<Estudiante> buscarPorRut(String rut);

    /**
     * Arma la ficha completa del estudiante para la vista perfil
     * @param rut del estudiante
     * @return FichaEstudianteDto con todos los datos
     */
    FichaEstudianteDto obtenerFicha(String rut);

    /**
     * Lista las asistencias de un estudiante por su RUT
     * @param rut del estudiante
     * @return lista de asistencias del estudiante
     */
    List<Asistencia> listarAsistenciasPorRut(String rut);

    // Métodos API REST con DTOs
    List<EstudianteResponseDto> listarEstudiantesApi();
    EstudianteResponseDto crearEstudianteApi(EstudianteRequestDto estudianteRequestDto);
    EstudianteResponseDto estudiantePorIdApi(Integer id);
    EstudianteResponseDto actualizarEstudianteApi(EstudianteRequestDto estudianteRequestDto, Integer id);
    void borrarEstudianteApi(Integer id);
}
