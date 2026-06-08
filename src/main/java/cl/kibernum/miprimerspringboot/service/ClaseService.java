package cl.kibernum.miprimerspringboot.service;

import cl.kibernum.miprimerspringboot.bl.entity.Clase;
import cl.kibernum.miprimerspringboot.dto.request.ClaseRequestDto;
import cl.kibernum.miprimerspringboot.dto.response.ClaseResponseDto;

import java.util.List;
import java.util.Optional;

/**
 * INTERFAZ DEL SERVICIO DE CLASES
 * ────────────────────────────────
 * Define las operaciones para gestionar los tipos de clase impartidos en la academia.
 * Implementada por ClaseServiceImpl.
 */
public interface ClaseService {

    /** Retorna todas las clases de la BD. Usado en el listado y en dropdowns de formularios. */
    List<Clase> listarClases();

    /**
     * Busca una clase por su ID.
     * Optional obliga al llamador a manejar el caso en que no exista.
     */
    Optional<Clase> clasePorId(Integer id);

    /** Crea o actualiza una clase. */
    Clase crearClase(Clase clase);

    /** Elimina una clase por ID. */
    void borrarClase(Integer id);

    // ── MÉTODOS API REST ────────────────────────────────────────────────

    /** Lista todas las clases como DTOs para la API. */
    List<ClaseResponseDto> listarClasesApi();

    /** Busca una clase por ID y la retorna como DTO. */
    ClaseResponseDto clasePorIdApi(Integer id);

    /** Crea una clase desde un RequestDto. */
    ClaseResponseDto crearClaseApi(ClaseRequestDto claseRequestDto);

    /** Actualiza los campos de una clase existente. */
    ClaseResponseDto actualizarClaseApi(ClaseRequestDto claseRequestDto, Integer id);

    /** Elimina una clase por ID (versión API). */
    void borrarClaseApi(Integer id);
}
