package cl.kibernum.miprimerspringboot.service;

import cl.kibernum.miprimerspringboot.bl.entity.Grado;
import cl.kibernum.miprimerspringboot.dto.request.GradoRequestDto;
import cl.kibernum.miprimerspringboot.dto.response.GradoResponseDto;

import java.util.List;
import java.util.Optional;

/**
 * INTERFAZ DEL SERVICIO DE GRADOS
 * ────────────────────────────────
 * Define las operaciones disponibles para gestionar la escala de grados/cinturones.
 * Implementada por GradoServiceImpl.
 *
 * Los métodos sin "Api" trabajan con la entidad Grado directamente (para vistas web).
 * Los métodos con "Api" trabajan con DTOs (para la API REST).
 */
public interface GradoService {

    /** Retorna la lista completa de grados almacenados en la BD. */
    List<Grado> listarGrados();

    /**
     * Busca un grado por su ID.
     * Retorna Optional<Grado> para forzar al código que llama a verificar si existe.
     */
    Optional<Grado> gradoPorId(Integer id);

    /**
     * Crea un grado nuevo o actualiza uno existente.
     * Si el objeto Grado tiene ID → UPDATE; sin ID → INSERT.
     */
    Grado crearGrado(Grado grado);

    /** Elimina un grado por su ID. Fallará si algún estudiante/instructor lo tiene asignado. */
    void borrarGrado(Integer id);

    // ── MÉTODOS API REST ────────────────────────────────────────────────

    /** Lista todos los grados como DTOs para la API. */
    List<GradoResponseDto> listarGradosApi();

    /** Crea un grado desde un RequestDto y retorna el ResponseDto con el ID generado. */
    GradoResponseDto crearGradoApi(GradoRequestDto gradoRequestDto);

    /** Busca un grado por ID y lo retorna como ResponseDto. */
    GradoResponseDto gradoPorIdApi(Integer id);

    /** Actualiza los campos de un grado existente. */
    GradoResponseDto actualizarGradoApi(GradoRequestDto gradoRequestDto, Integer id);

    /** Elimina un grado por ID (versión API). */
    void borrarGradoApi(Integer id);
}
