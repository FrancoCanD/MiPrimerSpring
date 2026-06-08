package cl.kibernum.miprimerspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * CLASE PRINCIPAL DE LA APLICACIÓN
 * ──────────────────────────────────
 * Es el punto de entrada de toda la aplicación Spring Boot.
 * Cuando se ejecuta el método main(), Spring Boot:
 *   1. Inicializa el contexto de Spring (crea todos los @Bean, @Service, @Repository, etc.)
 *   2. Configura el servidor Tomcat embebido en el puerto 8080
 *   3. Ejecuta data.sql para crear y poblar la base de datos
 *   4. La aplicación queda lista para recibir peticiones HTTP
 *
 * @SpringBootApplication es una anotación compuesta que equivale a:
 *   @Configuration     → esta clase puede definir Beans
 *   @EnableAutoConfiguration → Spring Boot configura automáticamente Thymeleaf, JPA, Security, etc.
 *   @ComponentScan     → escanea el paquete actual y subpaquetes buscando @Component, @Service, etc.
 */
@SpringBootApplication
public class MiPrimerSpringbootApplication {

    /**
     * Método main: punto de entrada estándar de Java.
     * SpringApplication.run() arranca el servidor y la aplicación completa.
     */
    public static void main(String[] args) {
        SpringApplication.run(MiPrimerSpringbootApplication.class, args);
    }
}
