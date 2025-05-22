package com.Digis01.FArceProgramacionNCapas.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() //peticiones AJAX se protejen con tambien se puede solucionar dando los permisos en el EndPoint para no tener riesgos de seguridad
                .authorizeHttpRequests((requests) -> requests
                    .requestMatchers("/login").permitAll() // Acesso al login de manera libre
                    .requestMatchers(HttpMethod.GET, "/CargaMasiva").hasRole("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.GET, "/consultaDatos").hasRole("ANALISTA") // Se agrega HttpMethod.GET, para limitar el verbo HTTP a utilizar
                    //.requestMatchers("/consultaDatos").hasRole("ANALISTA")

                    .anyRequest().hasRole("PROGRAMADOR") // Acceso completo
                )
                .formLogin((form) -> form
                    .permitAll()
                    //.defaultSuccessUrl("/Usuario", true) // Redireccion despues de login exitoso
                    // .loginPage("/login") ruta a futuro para implementar un login propio
                    .successHandler((request, response, authentication) -> {
                        var roles = authentication.getAuthorities().toString();
                        
                        if (roles.contains("ROLE_PROGRAMADOR")) {
                            response.sendRedirect("/Usuario");
                        } else if (roles.contains("ROLE_ADMINISTRADOR")) {
                            response.sendRedirect("/Usuario/CargaMasiva");
                        } else if (roles.contains("ROLE_ANALISTA")) {
                            response.sendRedirect("/consultaDatos");
                        } else {
                            response.sendRedirect("/access-denied");
                        }
                    })
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    // Creacion de los Usuarios en Memoria
    @Bean
    public UserDetailsService userDetailsService() {
        /*
            Para pruebas se usa withDefaultPasswordEncoder
                -> Este método usa un codificador de contraseñas inseguro (simple en memoria)
            Para produccion se usa PasswordEncoder como BCryptPasswordEncoder
                -> Aplica un hashing seguro a las contraseñas
         */

        UserDetails programador = User.withDefaultPasswordEncoder()
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
                .build();

        return new InMemoryUserDetailsManager(programador, administrador, analista);
    }
}
