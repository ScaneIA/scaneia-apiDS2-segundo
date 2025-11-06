package org.example.scaneia_dsii.service;

import io.jsonwebtoken.*;
import jakarta.persistence.EntityNotFoundException;
import org.example.scaneia_dsii.config.JwtProperties;
import org.example.scaneia_dsii.dtos.CadastroRequestDTO;
import org.example.scaneia_dsii.model.Usuario;
import org.example.scaneia_dsii.model.UsuarioAcessoLogDau;
import org.example.scaneia_dsii.repository.UsuarioAcessoLogDauRepository;
import org.example.scaneia_dsii.repository.UsuarioRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.*;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {

    private final UsuarioService usuarioService;
    private final RedisTemplate<String, String> redisTemplate;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioAcessoLogDauRepository usuarioAcessoLogDauRepository;
    private final JwtProperties jwtProperties;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public JwtService(
            UsuarioService usuarioService,
            RedisTemplate<String, String> redisTemplate,
            UsuarioRepository usuarioRepository,
            UsuarioAcessoLogDauRepository usuarioAcessoLogDauRepository,
            JwtProperties jwtProperties) {
        this.usuarioService = usuarioService;
        this.redisTemplate = redisTemplate;
        this.usuarioRepository = usuarioRepository;
        this.usuarioAcessoLogDauRepository = usuarioAcessoLogDauRepository;
        this.jwtProperties = jwtProperties;
    }

    // 🔑 Converte a string da secret em SecretKey segura
    private SecretKey getAccessKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getAccessSecret().getBytes(StandardCharsets.UTF_8));
    }

    private SecretKey getRefreshKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getRefreshSecret().getBytes(StandardCharsets.UTF_8));
    }

    // Gera Access Token
    public String gerarAccessToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        String usuarioTipo = usuarioService.recuperarTipoUsuario(username);
        Map<String, Object> map = usuarioService.recuperarIds(username);
        claims.put("username", username);
        claims.putAll(map);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username + "|" + usuarioTipo)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getAccessExpiration()))
                .signWith(getAccessKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Gera Refresh Token e salva no Redis
    public String gerarRefreshToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        String usuarioTipo = usuarioService.recuperarTipoUsuario(username);
        Map<String, Object> map = usuarioService.recuperarIds(username);
        claims.putAll(map);

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(username + "|" + usuarioTipo)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getRefreshExpiration()))
                .signWith(getRefreshKey(), SignatureAlgorithm.HS256)
                .compact();

        redisTemplate.opsForValue().set(username, token, jwtProperties.getRefreshExpiration(), TimeUnit.MILLISECONDS);
        System.out.println("Token salvo no Redis: " + token);
        return token;
    }

    public boolean validarAccessToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getAccessKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String extrairUsername(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getAccessKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            String subject = claims.getSubject();
            return subject.split("\\|")[0];
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token expirado");
        } catch (JwtException e) {
            throw new RuntimeException("Token inválido");
        }
    }

    // 🔍 Extrai tipo de usuário do Refresh Token

    public String extrairUsuarioTipoRefreshToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getRefreshKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            String subject = claims.getSubject();
            return subject.split("\\|")[1];
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token expirado");
        } catch (JwtException e) {
            throw new RuntimeException("Token inválido");
        }
    }

    public boolean validarRefreshToken(String token) {
        return redisTemplate.hasKey(token);
    }

    public String extrairUsernameRefreshToken(String token) {
        return redisTemplate.opsForValue().get(token);
    }

    public void revogarRefreshToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getRefreshKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        String email = claims.get("username", String.class);
        Usuario user = usuarioRepository.findByEmail(email);

        UsuarioAcessoLogDau log = usuarioAcessoLogDauRepository
                .findUltimoAcessoPorUsuario(user.getId())
                .orElseThrow(() -> new RuntimeException("Nenhum acesso ativo encontrado."));

        log.setDataLogout(OffsetDateTime.now());
        usuarioAcessoLogDauRepository.save(log);

        redisTemplate.delete(token);
    }

    public boolean salvarNovaSenha(CadastroRequestDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail());
        System.out.println(usuario);
        if (usuario == null) {
            throw new EntityNotFoundException("Usuário não encontrado");
        }
        System.out.println(usuario.getSenha());
        if (!Objects.equals(usuario.getSenha(), "")) {
            return false;
        }
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        System.out.println(passwordEncoder.encode(dto.getSenha()));
        usuarioRepository.save(usuario);
        return true;
    }
}
