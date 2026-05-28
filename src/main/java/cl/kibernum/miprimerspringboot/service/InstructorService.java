package cl.kibernum.miprimerspringboot.service;

import cl.kibernum.miprimerspringboot.bl.entity.Instructor;
import cl.kibernum.miprimerspringboot.dto.request.InstructorRequestDto;
import cl.kibernum.miprimerspringboot.dto.response.InstructorResponseDto;

import java.util.List;
import java.util.Optional;

/**
 * Interface del servicio de instructor
 * contiene los metodos abstractos del Instructor
 */
public interface InstructorService {

    /**
     * Lista de Instructores existentes en la BD
     * @return Lista de Instructores de la BD
     */
    List<Instructor> listarInstructores();

    /**
     * Buscar al instructor por id
     * @param id del instructor
     * @return el instructor según id
     */
    Optional<Instructor> instructorPorId(Integer id);

    /**
     * Crea un instructor nuevo o actualiza uno existente
     * @param instructor (objeto de tipo Instructor)
     * @return Instructor guardado
     */
    Instructor crearInstructor(Instructor instructor);

    /**
     * Borra un instructor según ID
     * @param id del instructor a eliminar
     */
    void borrarInstructor(Integer id);

    // Métodos API REST con DTOs
    List<InstructorResponseDto> listarInstructoresApi();
    InstructorResponseDto crearInstructorApi(InstructorRequestDto instructorRequestDto);
    InstructorResponseDto instructorPorIdApi(Integer id);
    InstructorResponseDto actualizarInstructorApi(InstructorRequestDto instructorRequestDto, Integer id);
    void borrarInstructorApi(Integer id);
}

