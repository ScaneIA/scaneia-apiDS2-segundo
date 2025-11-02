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

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                //Qualquer usuário pode fazer sem autenticação
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/ping").permitAll()
                .requestMatchers("/usuarios/hierarquia/**").permitAll()

                //Temporário
                .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()

                //Qualquer usuário pode fazer com autenticação
                .requestMatchers(HttpMethod.GET, "/usuarios/filtro").hasAnyRole("OPERARIO", "ADMIN", "DIRETOR", "RH")

                //Somente visualização dos usuários e estruturas para administradores e diretores
                .requestMatchers(HttpMethod.GET, "/usuarios/**").hasAnyRole("ADMIN", "DIRETOR")
                .requestMatchers(HttpMethod.GET, "/usuarioTipo**").hasAnyRole("ADMIN", "DIRETOR")
                .requestMatchers(HttpMethod.GET, "/estruturaTipo/**").hasAnyRole("ADMIN", "DIRETOR")
                .requestMatchers(HttpMethod.GET, "/estrutura/**").hasAnyRole("ADMIN", "DIRETOR")

                //Todos os acessos as rotas para os analistas de RH
                .requestMatchers("/usuarios/**").hasRole("RH")
                .requestMatchers("/usuarioTipo/**").hasRole("RH")
                .requestMatchers("/estrutura/**").hasRole("RH")
                .requestMatchers("/estruturaTipo/**").hasRole("RH")
                .requestMatchers("/plano/**").hasRole("RH")
                .requestMatchers("/planoDetalhe/**").hasRole("RH")
                .requestMatchers("/transacao/**").hasRole("RH")
                .requestMatchers("/transacaoItem/**").hasRole("RH")

                .requestMatchers("/vision/analyze").hasAnyRole("COLABORADOR")


                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
