package cl.kibernum.miprimerspringboot;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * UTILIDAD PARA GENERAR CONTRASEÑAS HASHEADAS
 * ─────────────────────────────────────────────
 * Esta clase es una herramienta de desarrollo, NO forma parte de la aplicación.
 * Se usa para generar el hash BCrypt de una contraseña en texto plano,
 * y copiar ese hash al archivo data.sql para los usuarios de prueba.
 *
 * ¿Por qué hashear las contraseñas?
 * Las contraseñas NUNCA deben guardarse en texto plano en la BD.
 * Si alguien accede a la base de datos, no podrá leer las contraseñas reales.
 * BCrypt es un algoritmo de hash seguro: cada ejecución genera un resultado diferente.
 *
 * ¿Cómo usar esta clase?
 *   1. Cambia "passwordPlano" por la contraseña que quieras hashear.
 *   2. Ejecuta el método main (click derecho → Run en IntelliJ).
 *   3. Copia el hash impreso en la consola.
 *   4. Pégalo en el INSERT de data.sql para el usuario correspondiente.
 *
 * NOTA: Esta clase no debe subirse a producción.
 */
public class GenerarPassword {

    public static void main(String[] args) {

        // Creamos el encriptador BCrypt (el mismo que usa Spring Security para verificar)
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // La contraseña en texto plano que queremos hashear
        String passwordPlano = "1234";

        // Generamos el hash (resultado diferente cada vez, pero siempre válido)
        String passwordEncriptado = encoder.encode(passwordPlano);

        // Imprimimos el hash para copiarlo y pegarlo en data.sql
        System.out.println(passwordEncriptado);
    }
}
