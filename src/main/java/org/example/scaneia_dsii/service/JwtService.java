package org.example.scaneia_dsii.service;

import io.jsonwebtoken.*;
import org.example.scaneia_dsii.config.JwtProperties;
import org.example.scaneia_dsii.model.Usuario;
import org.example.scaneia_dsii.model.UsuarioAcessoLogDau;
import org.example.scaneia_dsii.repository.UsuarioAcessoLogDauRepository;
import org.example.scaneia_dsii.repository.UsuarioRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.*;


@Service
public class JwtService {

    private final JwtProperties jwtProperties;
    private final StringRedisTemplate redisTemplate;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioAcessoLogDauRepository usuarioAcessoLogDauRepository;

    public JwtService(JwtProperties jwtProperties, StringRedisTemplate redisTemplate, UsuarioRepository usuarioRepository, UsuarioAcessoLogDauRepository usuarioAcessoLogDauRepository) {
        this.jwtProperties = jwtProperties;
        this.redisTemplate = redisTemplate;
        this.usuarioRepository = usuarioRepository;
        this.usuarioAcessoLogDauRepository = usuarioAcessoLogDauRepository;
    }

    // Gera Access Token
    public String gerarAccessToken(String username, String usuarioTipo) {
        return Jwts.builder()
                .setSubject(username + "|" + usuarioTipo)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getAccessExpiration()))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getAccessSecret())
                .compact();
    }

    // Gera Refresh Token e salva no Redis
    public String gerarRefreshToken(String username, String usuarioTipo) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(username + "|" + usuarioTipo)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getRefreshExpiration()))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getRefreshSecret())
                .compact();

        redisTemplate.opsForValue().set(username, token, jwtProperties.getRefreshExpiration(), TimeUnit.MILLISECONDS);
        // Teste: ver se realmente salvou
        String tokenSalvo = redisTemplate.opsForValue().get(username);
        System.out.println("Token salvo no Redis: " + tokenSalvo);
        return token;
    }

    public boolean validarAccessToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtProperties.getAccessSecret()).parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String extrairUsername(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtProperties.getAccessSecret())
                    .parseClaimsJws(token)
                    .getBody();
            String subject = claims.getSubject();
            String[] partes = subject.split("\\|");
            return partes[0];
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token expirado");
        } catch (JwtException e) {
            throw new RuntimeException("Token inválido");
        }
    }
    public String extrairUsuarioTipoRefreshToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtProperties.getRefreshSecret())
                    .parseClaimsJws(token)
                    .getBody();
            String subject = claims.getSubject();
            String[] partes = subject.split("\\|");
            System.out.println(partes[1]);
            return partes[1];
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token expirado");
        } catch (JwtException e) {
            throw new RuntimeException("Token inválidooo");
        }
    }

    public boolean validarRefreshToken(String token) {
        return redisTemplate.hasKey(token);
    }

    public String extrairUsernameRefreshToken(String token) {
        return redisTemplate.opsForValue().get(token);
    }

    public void revogarRefreshToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtProperties.getRefreshSecret())
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
}

