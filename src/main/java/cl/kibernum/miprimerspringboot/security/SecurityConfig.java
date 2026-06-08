package cl.kibernum.miprimerspringboot.security;

import cl.kibernum.miprimerspringboot.service.serviceimpl.UsuarioDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * CLASE DE CONFIGURACIÓN DE SEGURIDAD
 * ─────────────────────────────────────
 * @Configuration le indica a Spring que esta clase contiene configuraciones
 * que deben procesarse al iniciar la aplicación.
 *
 * @EnableMethodSecurity(securedEnabled = true) activa la posibilidad de usar
 * la anotación @Secured directamente sobre métodos de controladores o servicios.
 * Ejemplo: @Secured("ROL_ADMIN") sobre un método bloquea el acceso a otros roles.
 *
 * JERARQUÍA DE ROLES DEL SISTEMA:
 *   ROL_SUPERADMIN → ROL_ADMIN + puede asignar ROL_ADMIN a otros usuarios
 *   ROL_ADMIN      → gestión completa del sistema
 *   ROL_INSTRUCTOR → gestión de clases, estudiantes y asistencias
 *   ROL_ESTUDIANTE → acceso limitado a sus propios datos
 */
@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    /**
     * BEAN: ENCRIPTADOR DE CONTRASEÑAS
     * ──────────────────────────────────
     * BCrypt es un algoritmo de encriptación unidireccional (no se puede revertir).
     * Cada vez que encripta la misma contraseña genera un hash diferente,
     * lo que lo hace muy seguro contra ataques de diccionario.
     *
     * Retornamos la interfaz PasswordEncoder (no BCryptPasswordEncoder directamente)
     * para no acoplar el código a una implementación específica.
     */
    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * BEAN: PROVEEDOR DE AUTENTICACIÓN
     * ─────────────────────────────────
     * DaoAuthenticationProvider verifica las credenciales del usuario contra la BD.
     * Necesita:
     *   1. UserDetailsService → sabe CÓMO cargar el usuario desde la BD
     *   2. PasswordEncoder    → sabe CÓMO comparar contraseña ingresada con hash en BD
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(
            UsuarioDetailsService usuarioDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(usuarioDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    /**
     * BEAN: CADENA DE SEGURIDAD PARA LA API REST
     * ───────────────────────────────────────────
     * @Order(1) → se evalúa PRIMERO. Solo aplica a rutas /api/**.
     *
     * Usa HTTP Basic Auth: el cliente envía usuario:contraseña en Base64 en cada petición.
     * CSRF deshabilitado porque HTTP Basic no usa formularios de navegador.
     *
     * ROLES EN LA API:
     *   ROL_SUPERADMIN tiene ROL_ADMIN también → accede a todas las rutas de admin.
     *   /api/usuarios → ROL_ADMIN y ROL_SUPERADMIN pueden crear/listar usuarios.
     */
    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurityFilterChain(
            HttpSecurity http,
            DaoAuthenticationProvider authenticationProvider) throws Exception {

        http
                .securityMatcher("/api/**")
                .authenticationProvider(authenticationProvider)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Gestión de grados: solo administradores
                        .requestMatchers("/api/grados/**").hasAnyAuthority("ROL_ADMIN", "ROL_SUPERADMIN")
                        // Gestión operativa: admin e instructor
                        .requestMatchers("/api/clases/**").hasAnyAuthority("ROL_ADMIN", "ROL_SUPERADMIN", "ROL_INSTRUCTOR")
                        .requestMatchers("/api/estudiantes/**").hasAnyAuthority("ROL_ADMIN", "ROL_SUPERADMIN", "ROL_INSTRUCTOR")
                        .requestMatchers("/api/instructores/**").hasAnyAuthority("ROL_ADMIN", "ROL_SUPERADMIN", "ROL_INSTRUCTOR")
                        .requestMatchers("/api/asistencias/**").hasAnyAuthority("ROL_ADMIN", "ROL_SUPERADMIN", "ROL_INSTRUCTOR")
                        // Gestión de usuarios: solo administradores (la restricción de ROL_ADMIN en
                        // la creación se refuerza a nivel de servicio, no solo aquí)
                        .requestMatchers("/api/usuarios/**").hasAnyAuthority("ROL_ADMIN", "ROL_SUPERADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(basic -> {});

        return http.build();
    }

    /**
     * BEAN: CADENA DE SEGURIDAD PARA LAS VISTAS WEB (THYMELEAF)
     * ───────────────────────────────────────────────────────────
     * @Order(2) → se evalúa DESPUÉS de la de API.
     * Usa formulario de login con sesión y cookies.
     *
     * NOTA SOBRE ROL_SUPERADMIN:
     * En data.sql, superAdmin tiene ROL_ADMIN + ROL_SUPERADMIN.
     * Como tiene ROL_ADMIN, puede acceder a todas las rutas que exigen ROL_ADMIN.
     * El ROL_SUPERADMIN se usa SOLO en el servicio para verificar si puede asignar ROL_ADMIN.
     */
    @Bean
    @Order(2)
    public SecurityFilterChain webSecurityFilterChain(
            HttpSecurity http,
            DaoAuthenticationProvider authenticationProvider) throws Exception {

        http
                .authenticationProvider(authenticationProvider)

                .authorizeHttpRequests(auth -> auth

                        // Rutas públicas (no requieren login)
                        .requestMatchers(
                                "/login",
                                "/css/**",
                                "/js/**",
                                "/img/**",
                                "/assets/**",
                                "/webjars/**"
                        ).permitAll()

                        // Swagger: solo administradores
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).hasAnyAuthority("ROL_ADMIN", "ROL_SUPERADMIN")

                        // Panel de administración (usuarios, grados): admin y superAdmin
                        .requestMatchers("/admin/**").hasAnyAuthority("ROL_ADMIN", "ROL_SUPERADMIN")
                        .requestMatchers("/grados/**").hasAnyAuthority("ROL_ADMIN", "ROL_SUPERADMIN")

                        // Operativa: admin, superAdmin e instructor
                        .requestMatchers("/clases/**").hasAnyAuthority("ROL_ADMIN", "ROL_SUPERADMIN", "ROL_INSTRUCTOR")
                        .requestMatchers("/instructores/**").hasAnyAuthority("ROL_ADMIN", "ROL_SUPERADMIN", "ROL_INSTRUCTOR")
                        .requestMatchers("/asistencias/**").hasAnyAuthority("ROL_ADMIN", "ROL_SUPERADMIN", "ROL_INSTRUCTOR")

                        // Estudiantes: todos los roles autenticados
                        .requestMatchers("/estudiantes/**").hasAnyAuthority("ROL_ADMIN", "ROL_SUPERADMIN", "ROL_INSTRUCTOR", "ROL_ESTUDIANTE")

                        .requestMatchers("/inicio").authenticated()
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("KENPOSESSION", "JSESSIONID")
                        .permitAll()
                )

                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/acceso-denegado")
                )

                .sessionManagement(session -> session
                        .invalidSessionUrl("/login?invalid=true")
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                );

        return http.build();
    }
}
