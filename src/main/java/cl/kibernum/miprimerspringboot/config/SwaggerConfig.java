package cl.kibernum.miprimerspringboot.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * CONFIGURACIÓN DE SWAGGER / OPENAPI
 * ─────────────────────────────────────
 * Swagger (o SpringDoc OpenAPI) genera automáticamente la documentación
 * interactiva de la API REST del proyecto.
 *
 * Acceso: http://localhost:8080/swagger-ui.html
 * (Solo para ROL_ADMIN según la configuración de SecurityConfig)
 *
 * La documentación se genera leyendo las anotaciones de los @RestController:
 *   - @Operation(summary = "...") → descripción de cada endpoint
 *   - Los @RequestBody y @PathVariable → parámetros esperados
 *   - Los tipos de retorno → estructura de la respuesta JSON
 *
 * Este Bean personaliza los metadatos de la documentación:
 * título, descripción y versión de la API.
 */
@Configuration
public class SwaggerConfig {

    /**
     * @Bean OpenAPI → registra la configuración de metadatos de la API.
     * Spring Boot + SpringDoc la detecta automáticamente y la aplica.
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MiPrimer Spring Boot REST API")
                        .description("API REST para gestionar la Escuela de Kenpo: " +
                                     "grados, estudiantes, instructores, clases y asistencias.")
                        .version("1.0.0"));
    }
}
