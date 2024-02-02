package io.github.AndCandido.apispringsecurity.config;

import io.github.AndCandido.apispringsecurity.security.AppSecurityFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig {

    private AppSecurityFilter appSecurityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/public").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                .anyRequest().authenticated()
            ).addFilterBefore(appSecurityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
