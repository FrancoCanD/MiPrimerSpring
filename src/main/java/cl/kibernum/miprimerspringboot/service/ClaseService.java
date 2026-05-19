package cl.kibernum.miprimerspringboot.service;

import cl.kibernum.miprimerspringboot.bl.entity.Clase;

import java.util.List;
import java.util.Optional;

/**
 * Interface del servicio de Clase
 * contiene los metodos abstractos del clase
 */

public interface ClaseService {
    /**
     * Lista todas las clases existentes en la BD
     * @return Lista de clases
     */
    List<Clase> listarClases();

    /**
     * Buscar clase por ID
     * @param idClase
     * @return Clase según id
     */
    Optional<Clase> clasePorId(Integer idClase);

    /**
     * Crear una nueva clase
     * @param clase (Objeto de tipo clase)
     * @return 1
     */
    Clase crearClase(Clase clase);

    /**
     * Borra una clase según id
     * @param idClase de la clase a borrar
     */
    void borrarClase(Integer idClase);
}
