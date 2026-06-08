package cl.kibernum.miprimerspringboot;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * INICIALIZADOR PARA DESPLIEGUE EN SERVIDOR EXTERNO
 * ───────────────────────────────────────────────────
 * Esta clase solo se necesita cuando se despliega la aplicación como un archivo WAR
 * en un servidor externo como Tomcat, JBoss o WildFly.
 *
 * Extiende SpringBootServletInitializer y sobreescribe configure() para indicarle
 * al servidor externo cómo iniciar la aplicación Spring Boot.
 *
 * Si la aplicación se ejecuta directamente con el JAR embebido (java -jar app.jar),
 * esta clase no tiene efecto; en ese caso el main() de MiPrimerSpringbootApplication basta.
 */
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        // Le dice al servidor qué clase principal usar para arrancar Spring Boot
        return application.sources(MiPrimerSpringbootApplication.class);
    }
}
