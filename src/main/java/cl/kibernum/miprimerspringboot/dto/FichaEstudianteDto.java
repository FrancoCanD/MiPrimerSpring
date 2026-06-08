package cl.kibernum.miprimerspringboot.dto;

import cl.kibernum.miprimerspringboot.bl.entity.Asistencia;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO ESPECIAL: FICHA COMPLETA DEL ESTUDIANTE
 * ─────────────────────────────────────────────
 * No es un DTO de API REST, sino un objeto de transferencia para la VISTA Thymeleaf.
 * Se usa exclusivamente en la vista "perfil-estudiante.html".
 *
 * ¿POR QUÉ NO USAR LA ENTIDAD ESTUDIANTE DIRECTAMENTE?
 * La vista de perfil necesita información calculada (edad) y datos combinados
 * que no existen en una sola entidad. Este DTO agrupa todo lo necesario.
 *
 * Es construido por EstudianteServiceImpl.obtenerFicha(rut):
 *   - Toma datos de la entidad Estudiante (nombre, RUT, grado, etc.)
 *   - Calcula la edad con Period.between()
 *   - Carga la lista de asistencias del estudiante
 *   - Lo empaqueta todo en este DTO para enviarlo a la vista
 */
@Getter
@Setter
public class FichaEstudianteDto {

    // ── DATOS PERSONALES ─────────────────────────────────────────────
    private String nombres;
    private String apellido1;
    private String apellido2;
    private String rut;
    /** Edad calculada en años completos a partir de fechaNac. */
    private int edad;
    private LocalDate fechaNac;

    // ── DATOS ACADÉMICOS ─────────────────────────────────────────────
    /** Nombre del grado (ej: "Verde") o "Sin grado" si no tiene asignado. */
    private String grado;
    private LocalDate fechaAscenso;
    private LocalDate fechaInscripcion;
    /** true = activo en la academia, false = dado de baja. */
    private boolean activo;

    // ── HISTORIAL DE ASISTENCIAS ─────────────────────────────────────
    /** Lista de todas las asistencias del estudiante, filtradas por su ID. */
    private List<Asistencia> asistencias;
}
