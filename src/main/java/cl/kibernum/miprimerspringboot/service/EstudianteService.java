package cl.kibernum.miprimerspringboot.service;

import cl.kibernum.miprimerspringboot.bl.entity.Estudiante;
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
     * @param id
     * @return el estudiante según id
     */
    Optional<Estudiante> estudiantePorId(Integer id);

    /**
     * Crea un estudiante nuevo
     * @param estudiante (objeto de tipo Estudiante)
     * @return
     */
    Estudiante crearEstudiante(Estudiante estudiante);

    /**
     * Borra un estudiante según ID
     * @param id
     */

    void borrarEstudiante(Integer id);

    List<EstudianteResponseDto> listarEstudiantesApi();
    EstudianteResponseDto crearEstudianteApi(EstudianteRequestDto estudianteRequestDto);
    EstudianteResponseDto estudiantePorIdApi(Integer id);
    EstudianteResponseDto actualizarEstudianteApi(EstudianteRequestDto estudianteRequestDto, Integer id);
    void borrarEstudianteApi(Integer id);
}
