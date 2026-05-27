package cl.kibernum.miprimerspringboot.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MiPrimer Spring Boot REST API")
                        .description("API REST para gestionar grados")
                        .version("1.0.0.0"));
    }
}
