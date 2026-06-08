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
 * Configura la seguridad central del proyecto
 */
@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    /**
     * Encriptador de contraseñas
     */
    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /**
     * Proveedor de autenticación contra BD
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(UsuarioDetailsService usuarioDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider =new DaoAuthenticationProvider(usuarioDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    /**
     * Seguridad para API Rest
     * usa HTTP Básico
     */
    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http, DaoAuthenticationProvider authenticationProvider) throws Exception {
        http
                .securityMatcher("/api/**")
                .authenticationProvider(authenticationProvider)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/api/grados/**").hasAuthority("ROL_ADMIN")
                        .requestMatchers("/api/clases/**").hasAnyAuthority("ROL_ADMIN", "ROL_INSTRUCTOR")
                        .requestMatchers("/api/estudiantes/**").hasAnyAuthority("ROL_ADMIN", "ROL_INSTRUCTOR")
                        .requestMatchers("/api/instructores/**").hasAnyAuthority("ROL_ADMIN", "ROL_INSTRUCTOR")
                        .requestMatchers("/api/asistencias/**").hasAnyAuthority("ROL_ADMIN", "ROL_INSTRUCTOR")
                        .anyRequest().authenticated()
                )
                .httpBasic(basic -> {});
        return http.build();
    }

    /**
     * Seguridad para vistas web Thymeleaf.
     * Usa formulario de login.
     */
    @Bean
    @Order(2)
    public SecurityFilterChain webSecurityFilterChain(
            HttpSecurity http,
            DaoAuthenticationProvider authenticationProvider
    ) throws Exception {
        http
                .authenticationProvider(authenticationProvider)
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas
                        .requestMatchers(
                                "/login",
                                "/css/**",
                                "/js/**",
                                "/img/**",
                                "/assets/**",
                                "/webjars/**"
                        ).permitAll()
                        // Swagger solo para ADMIN
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).hasAuthority("ROL_ADMIN")
                        // Panel de administración de usuarios
                        .requestMatchers("/admin/**").hasAuthority("ROL_ADMIN")
                        // Vistas por rol
                        .requestMatchers("/grados/**").hasAuthority("ROL_ADMIN")
                        .requestMatchers("/clases/**").hasAnyAuthority("ROL_ADMIN", "ROL_INSTRUCTOR")
                        .requestMatchers("/instructores/**").hasAnyAuthority("ROL_ADMIN", "ROL_INSTRUCTOR")
                        .requestMatchers("/estudiantes/**").hasAnyAuthority("ROL_ADMIN", "ROL_INSTRUCTOR", "ROL_ESTUDIANTE")
                        .requestMatchers("/asistencias/**").hasAnyAuthority("ROL_ADMIN", "ROL_INSTRUCTOR")
                        // Inicio requiere login
                        .requestMatchers("/inicio").authenticated()
                        // Para todas las demás cosas requiere autenticación
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
