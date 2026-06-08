package cl.kibernum.miprimerspringboot.service;

import cl.kibernum.miprimerspringboot.bl.entity.Instructor;
import cl.kibernum.miprimerspringboot.dto.request.InstructorRequestDto;
import cl.kibernum.miprimerspringboot.dto.response.InstructorResponseDto;

import java.util.List;
import java.util.Optional;

/**
 * INTERFAZ DEL SERVICIO DE INSTRUCTORES
 * ───────────────────────────────────────
 * Define las operaciones para gestionar el cuerpo docente de la academia.
 * Implementada por InstructorServiceImpl.
 */
public interface InstructorService {

    /** Retorna la lista completa de instructores. */
    List<Instructor> listarInstructores();

    /** Busca un instructor por ID. Optional porque puede no existir. */
    Optional<Instructor> instructorPorId(Integer id);

    /** Crea un instructor nuevo o actualiza uno existente. */
    Instructor crearInstructor(Instructor instructor);

    /** Elimina un instructor por ID. */
    void borrarInstructor(Integer id);

    // ── MÉTODOS API REST ────────────────────────────────────────────────

    List<InstructorResponseDto> listarInstructoresApi();
    InstructorResponseDto crearInstructorApi(InstructorRequestDto instructorRequestDto);
    InstructorResponseDto instructorPorIdApi(Integer id);
    InstructorResponseDto actualizarInstructorApi(InstructorRequestDto instructorRequestDto, Integer id);
    void borrarInstructorApi(Integer id);
}
