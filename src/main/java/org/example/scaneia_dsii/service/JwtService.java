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

import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {

    private final JwtProperties jwtProperties;
    private final StringRedisTemplate redisTemplate;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioAcessoLogDauRepository usuarioAcessoLogDauRepository;
    private final UsuarioService usuarioService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final byte[] accessSecret;
    private final byte[] refreshSecret;

    public JwtService(
            JwtProperties jwtProperties,
            StringRedisTemplate redisTemplate,
            UsuarioService usuarioService,
            UsuarioRepository usuarioRepository,
            UsuarioAcessoLogDauRepository usuarioAcessoLogDauRepository
    ) {
        this.jwtProperties = jwtProperties;
        this.redisTemplate = redisTemplate;
        this.usuarioRepository = usuarioRepository;
        this.usuarioAcessoLogDauRepository = usuarioAcessoLogDauRepository;
        this.usuarioService = usuarioService;

        this.accessSecret = jwtProperties.getAccessSecret().getBytes(StandardCharsets.UTF_8);
        this.refreshSecret = jwtProperties.getRefreshSecret().getBytes(StandardCharsets.UTF_8);
    }

    public String gerarAccessToken(String username) {
        String usuarioTipo = usuarioService.recuperarTipoUsuario(username);
        Map<String, Object> claims = new HashMap<>(usuarioService.recuperarIds(username));
        claims.put("username", username);
        claims.put("usuario_tipo", usuarioTipo);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username + "|" + usuarioTipo)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getAccessExpiration()))
                .signWith(SignatureAlgorithm.HS256, accessSecret)
                .compact();
    }

    public String gerarRefreshToken(String username) {
        String usuarioTipo = usuarioService.recuperarTipoUsuario(username);
        Map<String, Object> claims = new HashMap<>(usuarioService.recuperarIds(username));
        claims.put("username", username);
        claims.put("usuario_tipo", usuarioTipo);

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(username + "|" + usuarioTipo)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getRefreshExpiration()))
                .signWith(SignatureAlgorithm.HS256, refreshSecret)
                .compact();

        redisTemplate.opsForValue().set(username, token, jwtProperties.getRefreshExpiration(), TimeUnit.MILLISECONDS);
        return token;
    }

    public boolean validarAccessToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(accessSecret)
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
                    .setSigningKey(accessSecret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.get("username", String.class);
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Access token expirado");
        } catch (JwtException e) {
            throw new RuntimeException("Access token inválido");
        }
    }

    public String extrairUsuarioTipoRefreshToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(refreshSecret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.get("usuario_tipo", String.class);
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Refresh token expirado");
        } catch (JwtException e) {
            throw new RuntimeException("Refresh token inválido");
        }
    }

    public boolean validarRefreshToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(refreshSecret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.get("username", String.class);
            String storedToken = redisTemplate.opsForValue().get(username);

            return storedToken != null && storedToken.equals(token);
        } catch (JwtException e) {
            return false;
        }
    }

    public String extrairUsernameRefreshToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(refreshSecret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.get("username", String.class);
        } catch (JwtException e) {
            throw new RuntimeException("Token inválido");
        }
    }

    public void revogarRefreshToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(refreshSecret)
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

            redisTemplate.delete(email);
        } catch (JwtException e) {
            throw new RuntimeException("Refresh token inválido ou já revogado");
        }
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
