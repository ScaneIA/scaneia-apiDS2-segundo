package org.example.scaneia_dsii.service;

import io.jsonwebtoken.*;
import org.example.scaneia_dsii.config.JwtProperties;
import org.example.scaneia_dsii.model.Usuario;
import org.example.scaneia_dsii.model.UsuarioAcessoLogDau;
import org.example.scaneia_dsii.repository.UsuarioAcessoLogDauRepository;
import org.example.scaneia_dsii.repository.UsuarioRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {

    private final JwtProperties jwtProperties;
    private final StringRedisTemplate redisTemplate;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioAcessoLogDauRepository usuarioAcessoLogDauRepository;
    private final UsuarioService usuarioService;

    public JwtService(JwtProperties jwtProperties,
                      StringRedisTemplate redisTemplate,
                      UsuarioService usuarioService,
                      UsuarioRepository usuarioRepository,
                      UsuarioAcessoLogDauRepository usuarioAcessoLogDauRepository) {
        this.jwtProperties = jwtProperties;
        this.redisTemplate = redisTemplate;
        this.usuarioRepository = usuarioRepository;
        this.usuarioAcessoLogDauRepository = usuarioAcessoLogDauRepository;
        this.usuarioService = usuarioService;
    }

    // -------------------- ACCESS TOKEN --------------------
    public String gerarAccessToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("usuario_tipo", usuarioService.recuperarTipoUsuario(username));
        claims.putAll(usuarioService.recuperarIds(username));

        byte[] secret = jwtProperties.getAccessSecret().getBytes(StandardCharsets.UTF_8);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getAccessExpiration()))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean validarAccessToken(String token) {
        try {
            byte[] secret = jwtProperties.getAccessSecret().getBytes(StandardCharsets.UTF_8);
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            System.out.println("TRUEEE");
            return true;
        } catch (JwtException e) {
            System.out.println("FALSE:: " + e.getMessage());
            return false;
        }
    }

    public String extrairUsername(String token) {
        try {
            byte[] secret = jwtProperties.getAccessSecret().getBytes(StandardCharsets.UTF_8);
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            return claims.get("username", String.class);
        } catch (JwtException e) {
            throw new RuntimeException("Token inválido ou expirado");
        }
    }

    public String extrairUsuarioTipo(String token) {
        try {
            byte[] secret = jwtProperties.getAccessSecret().getBytes(StandardCharsets.UTF_8);
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            return claims.get("usuario_tipo", String.class);
        } catch (JwtException e) {
            throw new RuntimeException("Token inválido ou expirado");
        }
    }

    // -------------------- REFRESH TOKEN --------------------
    public String gerarRefreshToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("usuario_tipo", usuarioService.recuperarTipoUsuario(username));
        claims.putAll(usuarioService.recuperarIds(username));

        byte[] secret = jwtProperties.getRefreshSecret().getBytes(StandardCharsets.UTF_8);

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getRefreshExpiration()))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        // Redis: username as key, token as value
        redisTemplate.opsForValue().set(username, token, jwtProperties.getRefreshExpiration(), TimeUnit.MILLISECONDS);

        return token;
    }

    public boolean validarRefreshToken(String token) {
        try {
            byte[] secret = jwtProperties.getRefreshSecret().getBytes(StandardCharsets.UTF_8);
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            String username = claims.get("username", String.class);

            String storedToken = redisTemplate.opsForValue().get(username);
            return token.equals(storedToken);

        } catch (JwtException e) {
            return false;
        }
    }

    public String extrairUsernameRefreshToken(String token) {
        try {
            byte[] secret = jwtProperties.getRefreshSecret().getBytes(StandardCharsets.UTF_8);
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            return claims.get("username", String.class);
        } catch (JwtException e) {
            throw new RuntimeException("Refresh token inválido ou expirado");
        }
    }

    public String extrairUsuarioTipoRefreshToken(String token) {
        try {
            byte[] secret = jwtProperties.getRefreshSecret().getBytes(StandardCharsets.UTF_8);
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            return claims.get("usuario_tipo", String.class);
        } catch (JwtException e) {
            throw new RuntimeException("Refresh token inválido ou expirado");
        }
    }

    public void revogarRefreshToken(String token) {
        try {
            byte[] secret = jwtProperties.getRefreshSecret().getBytes(StandardCharsets.UTF_8);
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            String username = claims.get("username", String.class);

            if (username != null) {
                Usuario user = usuarioRepository.findByEmail(username);
                if (user != null) {
                    UsuarioAcessoLogDau log = usuarioAcessoLogDauRepository
                            .findUltimoAcessoPorUsuario(user.getId())
                            .orElse(null);
                    if (log != null) {
                        log.setDataLogout(OffsetDateTime.now());
                        usuarioAcessoLogDauRepository.save(log);
                    }
                }

                redisTemplate.delete(username);
            }
        } catch (JwtException ignored) {
        }
    }
}
