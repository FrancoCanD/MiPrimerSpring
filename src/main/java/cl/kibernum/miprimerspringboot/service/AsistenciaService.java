package cl.kibernum.miprimerspringboot.service;

import cl.kibernum.miprimerspringboot.bl.entity.Asistencia;
import cl.kibernum.miprimerspringboot.dto.request.AsistenciaRequestDto;
import cl.kibernum.miprimerspringboot.dto.response.AsistenciaResponseDto;

import java.util.List;
import java.util.Optional;

/**
 * INTERFAZ DEL SERVICIO DE ASISTENCIAS
 * ──────────────────────────────────────
 * Define los contratos (operaciones disponibles) para la gestión de asistencias.
 * La implementación real está en AsistenciaServiceImpl.
 *
 * Esta interfaz tiene DOS grupos de métodos:
 *
 * 1. Métodos para las VISTAS WEB (trabajan con entidades directamente):
 *    Usados por AsistenciaController (Thymeleaf).
 *
 * 2. Métodos para la API REST (trabajan con DTOs):
 *    Usados por AsistenciaRestController.
 *    Los DTOs evitan exponer la estructura interna de la entidad hacia afuera.
 */
public interface AsistenciaService {

    /** Retorna todas las asistencias. Usado en la vista de listado. */
    List<Asistencia> listarTodas();

    /** Busca una asistencia por ID. Retorna Optional por si no existe. */
    Optional<Asistencia> buscarPorId(Integer id);

    /** Igual que buscarPorId pero retorna null si no existe (más simple para algunas vistas). */
    Asistencia obtenerPorId(Integer id);

    /** Guarda (crea o actualiza) una asistencia en la BD. */
    void guardar(Asistencia asistencia);

    /** Elimina una asistencia por su ID. */
    void eliminar(Integer id);

    // ── MÉTODOS API REST ────────────────────────────────────────────────

    /** Retorna todas las asistencias formateadas como DTOs de respuesta para la API. */
    List<AsistenciaResponseDto> listarAsistenciasApi();

    /** Crea una asistencia desde los datos recibidos en un RequestDto y retorna el ResponseDto. */
    AsistenciaResponseDto crearAsistenciaApi(AsistenciaRequestDto asistenciaRequestDto);

    /** Busca una asistencia por ID y la retorna como DTO. Lanza excepción si no existe. */
    AsistenciaResponseDto asistenciaPorIdApi(Integer id);

    /** Actualiza los campos de una asistencia existente y retorna el resultado como DTO. */
    AsistenciaResponseDto actualizarAsistenciaApi(AsistenciaRequestDto asistenciaRequestDto, Integer id);

    /** Elimina una asistencia por ID (versión API). */
    void borrarAsistenciaApi(Integer id);
}
