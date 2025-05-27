package com.Digis01.FArceProgramacionNCapas.Configuration;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable() //peticiones AJAX se protejen con tambien se puede solucionar dando los permisos en el EndPoint para no tener riesgos de seguridad
//                .authorizeHttpRequests((requests) -> requests
//                    
//                        // Acceso publico
//                    .requestMatchers("/login").permitAll() // Acesso al login de manera libre
//                        
//                        // Acceso Administrador
//                    .requestMatchers(HttpMethod.GET, "/Usuario/CargaMasiva").hasAnyRole("ADMINISTRADOR", "PROGRAMADOR")
//                    .requestMatchers(HttpMethod.POST, "/Usuario/CargaMasiva").hasAnyRole("ADMINISTRADOR", "PROGRAMADOR")
//                    .requestMatchers(HttpMethod.GET, "/Usuario/CargaMasiva/Procesar").hasAnyRole("ADMINISTRADOR", "PROGRAMADOR")
//
//                        // Acceso Analista
//                    .requestMatchers(HttpMethod.GET, "/Usuario").hasAnyRole("ANALISTA", "PROGRAMADOR") // index
//                    .requestMatchers(HttpMethod.GET, "/Usuario/Form/*").hasAnyRole("ANALISTA", "PROGRAMADOR")
//                    .requestMatchers(HttpMethod.POST, "/Usuario/GetAllDinamico").hasAnyRole("ANALISTA", "PROGRAMADOR")
//                    .requestMatchers(HttpMethod.GET, "/Usuario/EstadoByIdPais/*").hasAnyRole("ANALISTA", "PROGRAMADOR")
//                    .requestMatchers(HttpMethod.GET, "/Usuario/MunicipioByIdEstado/*").hasAnyRole("ANALISTA", "PROGRAMADOR")
//                    .requestMatchers(HttpMethod.GET, "/Usuario/ColoniaByIdMunicipio/*").hasAnyRole("ANALISTA", "PROGRAMADOR")
//                    //.requestMatchers(HttpMethod.GET, "/Usuario").hasRole("ANALISTA") // Se agrega HttpMethod.GET, para limitar el verbo HTTP a utilizar
//                        
//                        // Acceso Programador
//                    //.requestMatchers("/Usuario/**").hasRole("PROGRAMADOR") // Acceso completo solo para PROGRAMADOR
//                    .anyRequest().hasRole("PROGRAMADOR") // Acceso completo solo para PROGRAMADOR
//                )
//                .formLogin((form) -> form
//                    .permitAll()
//                    //.defaultSuccessUrl("/Usuario", true) // Redireccion despues de login exitoso
//                    .successHandler((request, response, authentication) -> {
//                        var roles = authentication.getAuthorities().toString();
//
//                            // Redireccion dependiendo la condicion del rol (Ruta completa del controller)
//                        if (roles.contains("ROLE_PROGRAMADOR")) {
//                            response.sendRedirect("/Usuario");
//                        } else if (roles.contains("ROLE_ADMINISTRADOR")) {
//                            response.sendRedirect("/Usuario/CargaMasiva");
//                        } else if (roles.contains("ROLE_ANALISTA")) {
//                            response.sendRedirect("/Usuario");
//                        } else {
//                            response.sendRedirect("/access-denied");
//                        }
//                    })
//                )
//                .logout((logout) -> logout.permitAll())
//                .exceptionHandling((exceptions) -> exceptions
//                    .accessDeniedPage("/access-denied")
//                );
//
//        return http.build();
//    }

    // Creacion de los Usuarios en Memoria
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        
    /*
            Para pruebas se usa withDefaultPasswordEncoder
                -> Este método usa un codificador de contraseñas inseguro (simple en memoria)
            Para produccion se usa PasswordEncoder como BCryptPasswordEncoder
                -> Aplica un hashing seguro a las contraseñas
     */

        /*UserDetails programador = User.withDefaultPasswordEncoder()
                .username("programador")
                .password("1234")
                .roles("PROGRAMADOR")
                .build();

        UserDetails administrador = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("1234")
                .roles("ADMINISTRADOR")
                .build();

        UserDetails analista = User.withDefaultPasswordEncoder()
                .username("analista")
                .password("1234")
                .roles("ANALISTA")
                .build();*/
 
        UserDetails programador = User.builder()
                .username("programador")
                .password(passwordEncoder.encode("1234"))
                .roles("PROGRAMADOR")
                .build();

        UserDetails administrador = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("1234"))
                .roles("ADMINISTRADOR")
                .build();

        UserDetails analista = User.builder()
                .username("analista")
                .password(passwordEncoder.encode("1234"))
                .roles("ANALISTA")
                .build();

        return new InMemoryUserDetailsManager(programador, administrador, analista);
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeHttpRequests(
                        configure -> configure
                                .requestMatchers("/login", "/access-denied").permitAll()
                                .requestMatchers("/Usuario").hasAnyRole("ADMINISTRADOR", "PROGRAMADOR", "ANALISTA")
                                .requestMatchers(HttpMethod.POST, "/Usuario/GetAllDinamico").hasAnyRole("ADMINISTRADOR", "PROGRAMADOR", "ANALISTA")
                                .requestMatchers("/Usuario/CargaMasiva").hasAnyRole("ADMINISTRADOR", "PROGRAMADOR")
                                .requestMatchers(HttpMethod.GET, "/Usuario/**").hasAnyRole("ANALISTA", "PROGRAMADOR", "ADMINISTRADOR")
                                .requestMatchers(HttpMethod.POST, "/Usuario/**").hasRole("PROGRAMADOR")
                                .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                .permitAll()
                .successHandler((request, response, authentication) -> {
                    var roles = authentication.getAuthorities().toString();

                    // Redireccion dependiendo la condicion del rol (Ruta completa del controller)
                    if (roles.contains("ROLE_PROGRAMADOR")) {
                        response.sendRedirect("/Usuario");
                    } else if (roles.contains("ROLE_ADMINISTRADOR")) {
                        response.sendRedirect("/Usuario/CargaMasiva");
                    } else if (roles.contains("ROLE_ANALISTA")) {
                        response.sendRedirect("/Usuario");
                    } else {
                        response.sendRedirect("/access-denied");
                    }
                })
                )
                .logout((logout) -> logout
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                    response.setHeader("Pragma", "no-cache");
                    response.setDateHeader("Expires", 0);
                    response.sendRedirect("/login?logout");
                })
                )
                .exceptionHandling((exceptions) -> exceptions
                .accessDeniedPage("/access-denied")
                )
                .headers(headers -> headers
                .cacheControl(cache -> cache.disable())
                );

        return httpSecurity.build();
    }

    /*@Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {

        /*
            Para pruebas se usa withDefaultPasswordEncoder
                -> Este método usa un codificador de contraseñas inseguro (simple en memoria)
            Para produccion se usa PasswordEncoder como BCryptPasswordEncoder
                -> Aplica un hashing seguro a las contraseñas
         */
        /*UserDetails programador = User.builder()
                .username("programador")
                .password("{noop}1234")
                .roles("PROGRAMADOR")
                .build();

        UserDetails administrador = User.builder()
                .username("admin")
                .password("{noop}1234")
                .roles("ADMINISTRADOR")
                .build();

        UserDetails analista = User.builder()
                .username("analista")
                .password("{noop}1234")
                .roles("ANALISTA")
                .build();

        return new InMemoryUserDetailsManager(programador, administrador, analista);
    }*/

//    @Bean
//    public UserDetailsService jdbcUserDetailsService(DataSource dataSource) {
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//
//        jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT username, password, status FROM usuario WHERE username = ?");
//
//        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT username, nombrerol FROM rolmanager WHERE username = ?");
//
//        return jdbcUserDetailsManager;
//    }
}
