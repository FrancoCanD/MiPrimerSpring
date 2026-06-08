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
     * Al declararlo como @Bean, Spring lo crea una sola vez y lo reutiliza
     * en toda la aplicación (en el proveedor de autenticación y donde se necesite).
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
     * DaoAuthenticationProvider es el componente que Spring Security usa para
     * verificar credenciales. Necesita dos cosas:
     *
     *   1. Un UserDetailsService: sabe CÓMO cargar un usuario desde la base de datos.
     *      (aquí usamos nuestro UsuarioDetailsService)
     *
     *   2. Un PasswordEncoder: sabe CÓMO comparar la contraseña ingresada
     *      con el hash almacenado en la BD.
     *
     * Spring inyecta automáticamente los parámetros gracias a @Bean y @Autowired implícito.
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
     * @Order(1) significa que esta cadena se evalúa PRIMERO.
     * Solo aplica a rutas que empiecen con /api/** (securityMatcher).
     *
     * La API REST usa HTTP Basic Auth: el cliente envía usuario y contraseña
     * codificados en Base64 en cada petición (sin sesión ni cookies).
     * Esto es ideal para ser consumida por otras aplicaciones o Postman/Swagger.
     *
     * CSRF está deshabilitado porque HTTP Basic no usa formularios de navegador,
     * por lo que no es vulnerable al ataque Cross-Site Request Forgery.
     */
    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurityFilterChain(
            HttpSecurity http,
            DaoAuthenticationProvider authenticationProvider) throws Exception {

        http
                // Solo aplica esta configuración a rutas /api/**
                .securityMatcher("/api/**")
                .authenticationProvider(authenticationProvider)

                // Deshabilitamos CSRF porque la API no usa cookies de sesión
                .csrf(AbstractHttpConfigurer::disable)

                // Definimos qué rol puede acceder a cada endpoint
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/grados/**").hasAuthority("ROL_ADMIN")
                        .requestMatchers("/api/clases/**").hasAnyAuthority("ROL_ADMIN", "ROL_INSTRUCTOR")
                        .requestMatchers("/api/estudiantes/**").hasAnyAuthority("ROL_ADMIN", "ROL_INSTRUCTOR")
                        .requestMatchers("/api/instructores/**").hasAnyAuthority("ROL_ADMIN", "ROL_INSTRUCTOR")
                        .requestMatchers("/api/asistencias/**").hasAnyAuthority("ROL_ADMIN", "ROL_INSTRUCTOR")
                        // Cualquier otra ruta bajo /api/ requiere estar autenticado
                        .anyRequest().authenticated()
                )
                // Activamos autenticación HTTP Basic (usuario:contraseña en el header)
                .httpBasic(basic -> {});

        return http.build();
    }

    /**
     * BEAN: CADENA DE SEGURIDAD PARA LAS VISTAS WEB (THYMELEAF)
     * ───────────────────────────────────────────────────────────
     * @Order(2) significa que esta cadena se evalúa DESPUÉS de la de API.
     * Aplica a todas las rutas que no hayan sido capturadas por la cadena anterior.
     *
     * Usa formulario de login (HTML con usuario y contraseña).
     * Spring Security maneja automáticamente la sesión del navegador con cookies.
     */
    @Bean
    @Order(2)
    public SecurityFilterChain webSecurityFilterChain(
            HttpSecurity http,
            DaoAuthenticationProvider authenticationProvider) throws Exception {

        http
                .authenticationProvider(authenticationProvider)

                // ── AUTORIZACIÓN DE RUTAS ──────────────────────────────────────────
                .authorizeHttpRequests(auth -> auth

                        // Rutas públicas: cualquiera puede acceder sin iniciar sesión
                        .requestMatchers(
                                "/login",       // Página de login
                                "/css/**",      // Hojas de estilo
                                "/js/**",       // Scripts JavaScript
                                "/img/**",      // Imágenes en /img
                                "/assets/**",   // Imágenes y recursos estáticos
                                "/webjars/**"   // Librerías front-end (Bootstrap, etc.)
                        ).permitAll()

                        // Swagger (documentación de API) solo para administradores
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).hasAuthority("ROL_ADMIN")

                        // Panel de administración de usuarios: solo ROL_ADMIN
                        .requestMatchers("/admin/**").hasAuthority("ROL_ADMIN")

                        // Gestión de grados: solo administrador
                        .requestMatchers("/grados/**").hasAuthority("ROL_ADMIN")

                        // Gestión de clases, instructores y asistencias: admin o instructor
                        .requestMatchers("/clases/**").hasAnyAuthority("ROL_ADMIN", "ROL_INSTRUCTOR")
                        .requestMatchers("/instructores/**").hasAnyAuthority("ROL_ADMIN", "ROL_INSTRUCTOR")
                        .requestMatchers("/asistencias/**").hasAnyAuthority("ROL_ADMIN", "ROL_INSTRUCTOR")

                        // Gestión de estudiantes: cualquier rol autenticado puede ver
                        .requestMatchers("/estudiantes/**").hasAnyAuthority("ROL_ADMIN", "ROL_INSTRUCTOR", "ROL_ESTUDIANTE")

                        // La página de inicio requiere haber iniciado sesión
                        .requestMatchers("/inicio").authenticated()

                        // Cualquier otra ruta también requiere autenticación
                        .anyRequest().authenticated()
                )

                // ── CONFIGURACIÓN DEL FORMULARIO DE LOGIN ─────────────────────────
                .formLogin(form -> form
                        // URL donde está el formulario HTML de login (GET)
                        .loginPage("/login")
                        // URL que procesa el POST del formulario (Spring lo maneja automático)
                        .loginProcessingUrl("/login")
                        // Tras un login exitoso, redirige al dashboard principal
                        .defaultSuccessUrl("/", true)
                        // Si el login falla, redirige con el parámetro ?error=true
                        .failureUrl("/login?error=true")
                        // La página de login es pública (no requiere autenticación previa)
                        .permitAll()
                )

                // ── CONFIGURACIÓN DEL LOGOUT ───────────────────────────────────────
                .logout(logout -> logout
                        // URL que activa el logout (debe ser POST para evitar CSRF)
                        .logoutUrl("/logout")
                        // Tras cerrar sesión, redirige al login con ?logout=true
                        .logoutSuccessUrl("/login?logout=true")
                        // Destruye la sesión del servidor (libera memoria)
                        .invalidateHttpSession(true)
                        // Elimina las cookies del navegador
                        .deleteCookies("KENPOSESSION", "JSESSIONID")
                        .permitAll()
                )

                // ── MANEJO DE ERRORES DE ACCESO ────────────────────────────────────
                .exceptionHandling(ex -> ex
                        // Si un usuario autenticado intenta acceder a algo sin permiso,
                        // se le muestra esta página en vez de un error 403 genérico
                        .accessDeniedPage("/acceso-denegado")
                )

                // ── CONFIGURACIÓN DE SESIÓN ────────────────────────────────────────
                .sessionManagement(session -> session
                        // Si la sesión expiró o es inválida, redirige al login
                        .invalidSessionUrl("/login?invalid=true")
                        // Un mismo usuario solo puede tener 1 sesión activa a la vez
                        .maximumSessions(1)
                        // false = si ya hay una sesión activa, la nueva la reemplaza
                        // true  = bloquea el nuevo login si ya hay una sesión activa
                        .maxSessionsPreventsLogin(false)
                );

        return http.build();
    }
}
