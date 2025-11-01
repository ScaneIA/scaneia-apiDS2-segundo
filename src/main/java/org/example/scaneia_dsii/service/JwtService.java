package org.example.scaneia_dsii.service;

import io.jsonwebtoken.*;
import org.example.scaneia_dsii.config.JwtProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {

    private final JwtProperties jwtProperties;
    private final StringRedisTemplate redisTemplate;

    public JwtService(JwtProperties jwtProperties, StringRedisTemplate redisTemplate) {
        this.jwtProperties = jwtProperties;
        this.redisTemplate = redisTemplate;
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
        String token = Jwts.builder()
                .setSubject(username + "|" + usuarioTipo)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getRefreshExpiration()))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getRefreshSecret())
                .compact();
        redisTemplate.opsForValue().set(username, token, jwtProperties.getRefreshExpiration(), TimeUnit.MILLISECONDS);
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
            System.out.println("username");
            System.out.println(partes[0]);
            System.out.println(partes[1]);
            return partes[0];
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token expirado");
        } catch (JwtException e) {
            throw new RuntimeException("Token inválido");
        }
    }


    public String extrairUsuarioTipo(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtProperties.getAccessSecret())
                    .parseClaimsJws(token)
                    .getBody();
            String subject = claims.getSubject();
            String[] partes = subject.split("\\|");
            System.out.println("rolee");
            System.out.println(partes[0]);
            System.out.println(partes[1]);
            return partes[1];
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
            return claims.getSubject().split("\\|")[1];
        } catch (JwtException e) {
            throw new RuntimeException("Refresh token inválido");
        }
    }


    public boolean validarRefreshToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtProperties.getRefreshSecret())
                    .parseClaimsJws(token)
                    .getBody();

            String subject = claims.getSubject(); // e.g. "username|role"
            String[] partes = subject.split("\\|");
            String username = partes[0];

            String storedToken = redisTemplate.opsForValue().get(username);
            return token.equals(storedToken);

        } catch (JwtException e) {
            return false;
        }
    }


    public String extrairUsernameRefreshToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtProperties.getRefreshSecret())
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject().split("\\|")[0];
        } catch (JwtException e) {
            throw new RuntimeException("Refresh token inválido");
        }
    }

    public void revogarRefreshToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtProperties.getRefreshSecret())
                    .parseClaimsJws(token)
                    .getBody();
            String username = claims.getSubject().split("\\|")[0];
            redisTemplate.delete(username);
        } catch (JwtException e) {
        }
    }}

