package com.eventsystem.event_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration

@SecurityScheme(
        name = "basicAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)

public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users/register").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/events/**").hasAnyAuthority("USER", "ADMIN", "ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/events/**").hasAnyAuthority("USER", "ADMIN", "ROLE_USER", "ROLE_ADMIN")

                        .requestMatchers("/api/tickets/book").hasAnyAuthority("USER", "ADMIN", "ROLE_USER", "ROLE_ADMIN")

                        .requestMatchers("/api/tickets/all").hasAnyAuthority("ADMIN", "ROLE_ADMIN")
                        .requestMatchers("/api/reports/**").hasAnyAuthority("ADMIN", "ROLE_ADMIN")

                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

}