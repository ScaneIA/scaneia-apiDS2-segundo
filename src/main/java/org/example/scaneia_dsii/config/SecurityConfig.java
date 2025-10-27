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

                .requestMatchers("/usuarios/**").permitAll() //ok
                .requestMatchers("/estrutura/**").permitAll() //ok
                .requestMatchers("/planoDetalhe/**").permitAll()
                .requestMatchers("/plano/**").permitAll() //ok
                .requestMatchers("/transacaoItem/**").permitAll()
                .requestMatchers("/transacao/**").permitAll()
                .requestMatchers("/usuarioTipo/**").permitAll()

//                //Temporário
//                .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
//
//                //Qualquer usuário pode fazer com autenticação
//                .requestMatchers(HttpMethod.GET, "/usuarios/filtro").hasAnyAuthority("OPERARIO", "ADMIN", "DIRETOR", "RH")
//
//                //Somente visualização dos usuários e estruturas para administradores e diretores
//                .requestMatchers(HttpMethod.GET, "/usuarios**").hasAnyAuthority("ADMIN", "DIRETOR")
//                //.requestMatchers(HttpMethod.GET, "/usuarioTipo**").hasAnyAuthority("ADMIN", "DIRETOR")
//                .requestMatchers(HttpMethod.GET, "/estruturaTipo**").hasAnyAuthority("ADMIN", "DIRETOR")
//                .requestMatchers(HttpMethod.GET, "/estrutura**").hasAnyAuthority("ADMIN", "DIRETOR")
//
//                //Todos os acessos as rotas para os analistas de RH
//                .requestMatchers("/usuarios**").hasAuthority("RH")
//                .requestMatchers("/usuarioTipo**").hasRole("RH")
//                .requestMatchers("/estrutura**").hasAuthority("RH")
//                .requestMatchers("/estruturaTipo**").hasAuthority("RH")
//                .requestMatchers("/plano**").hasAuthority("RH")
//                .requestMatchers("/planoDetalhe**").hasAuthority("RH")
//                .requestMatchers("/transacao**").hasAuthority("RH")
//                .requestMatchers("/transacaoItem**").hasAuthority("RH")


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
