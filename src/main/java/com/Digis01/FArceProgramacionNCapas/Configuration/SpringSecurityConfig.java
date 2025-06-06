package com.Digis01.FArceProgramacionNCapas.Configuration;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        configure -> configure
                                .requestMatchers("/login", "/access-denied").permitAll()
                                .requestMatchers("/Usuario").hasAnyRole("ADMINISTRADOR", "PROGRAMADOR", "ANALISTA", "Administrador", "Usuario", "Comprador", "Visitnate")
                                .requestMatchers(HttpMethod.POST, "/Usuario/GetAllDinamico").hasAnyRole("ADMINISTRADOR", "Comprador", "PROGRAMADOR", "Administrador", "ANALISTA", "Usuario")
                                .requestMatchers("/Usuario/CargaMasiva").hasAnyRole("ADMINISTRADOR", "Comprador", "PROGRAMADOR", "Administrador")
                                .requestMatchers(HttpMethod.GET, "/Usuario/**").hasAnyRole("ANALISTA", "Visitante", "PROGRAMADOR", "Administrador", "ADMINISTRADOR", "Comprador")
                                .requestMatchers(HttpMethod.POST, "/Usuario/**").hasAnyRole("PROGRAMADOR", "Administrador")
                                .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                .loginPage("/Usuario/iniciarSesion")
                .loginProcessingUrl("/autenticateTheUser")
                .permitAll()
                .successHandler((request, response, authentication) -> {

                    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                    response.setHeader("Pragma", "no-cache");
                    response.setDateHeader("Expires", 0);

                    var roles = authentication.getAuthorities().toString();

                    if (roles.contains("ROLE_Administrador")) {//"ROLE_Administrador,ROLE_PROGRAMADOR"
                        response.sendRedirect("/Usuario");
                    } else if (roles.contains("ROLE_Comprador")) {
                        response.sendRedirect("/Usuario/CargaMasiva");
                    } else if (roles.contains("ROLE_Visitante")) {
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

    // Usuarios en memoria con operador noop
    /*@Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {

        
            //Para pruebas se usa withDefaultPasswordEncoder
                //-> Este método usa un codificador de contraseñas inseguro (simple en memoria)
            //Para produccion se usa PasswordEncoder como BCryptPasswordEncoder
                //-> Aplica un hashing seguro a las contraseñas
         
        UserDetails programador = User.builder()
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
    @Bean
    public UserDetailsService jdbcUserDetailsService(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT username, password, status FROM usuario WHERE username = ?");

        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "SELECT username, CONCAT('ROLE_',nombrerol) AS authority FROM rolmanager WHERE username = ?");

        return jdbcUserDetailsManager;
    }

}
