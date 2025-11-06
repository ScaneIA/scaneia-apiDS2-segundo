package org.example.scaneia_dsii.config;

import org.example.scaneia_dsii.filter.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Public routes
                .requestMatchers("/auth/**", "/ping", "/usuarios/hierarquia/**").permitAll()

                //Temporário
                .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()

                //Qualquer usuário pode fazer com autenticação
                .requestMatchers(HttpMethod.GET, "/usuarios/filtro").hasAnyRole("OPERARIO", "ADMIN", "DIRETOR", "RH")
                .requestMatchers(HttpMethod.GET, "/usuarios/perfil").permitAll()

                .requestMatchers(HttpMethod.GET, "/usuarios/filtro").hasAnyRole("COLABORADOR", "CHEFE_DE_AREA", "DIRETOR", "RH")

                //Somente visualização dos usuários e estruturas para administradores e diretores
                .requestMatchers(HttpMethod.GET, "/usuarios/**").hasAnyRole("CHEFE_DE_AREA", "DIRETOR")
                .requestMatchers(HttpMethod.GET, "/usuarioTipo**").hasAnyRole("CHEFE_DE_AREA", "DIRETOR")
                .requestMatchers(HttpMethod.GET, "/estruturaTipo/**").hasAnyRole("CHEFE_DE_AREA", "DIRETOR")
                .requestMatchers(HttpMethod.GET, "/estrutura/**").hasAnyRole("CHEFE_DE_AREA", "DIRETOR")

                //Todos os acessos as rotas para os analistas de RH
                .requestMatchers("/usuarios/**").hasRole("RH")
                .requestMatchers("/usuarioTipo/**").hasRole("RH")
                .requestMatchers("/estrutura/**").hasRole("RH")
                .requestMatchers("/estruturaTipo/**").hasRole("RH")
                .requestMatchers("/plano/**").hasRole("RH")
                .requestMatchers("/planoDetalhe/**").hasRole("RH")
                .requestMatchers("/transacao/**").hasRole("RH")
                .requestMatchers("/transacaoItem/**").hasRole("RH")
                .requestMatchers("/vision/analyze").hasRole("COLABORADOR")
                .anyRequest().authenticated()
            );

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Replace with your frontend URLs
        config.setAllowedOrigins(List.of(
                "http://localhost:5173",
                "https://scaneia-admin.web.app"
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
