package org.example.scaneia_dsii.controller;

import org.example.scaneia_dsii.dtos.*;
import org.example.scaneia_dsii.service.JwtService;
import org.example.scaneia_dsii.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final JwtService jwtService;

    public AuthController(UsuarioService usuarioService, JwtService jwtService) {
        this.usuarioService = usuarioService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO dto) {
        usuarioService.validarCredenciais(dto.getUsername(), dto.getPassword());

        String accessToken = jwtService.gerarAccessToken(dto.getUsername());
        String refreshToken = jwtService.gerarRefreshToken(dto.getUsername());

        return ResponseEntity.ok(new AuthResponseDTO(accessToken, refreshToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refresh(@RequestBody RefreshTokenRequestDTO dto) {
        if (!jwtService.validarRefreshToken(dto.getRefreshToken())) {
            return ResponseEntity.status(401).build();
        }

        String username = jwtService.extrairUsernameRefreshToken(dto.getRefreshToken());
        String accessToken = jwtService.gerarAccessToken(username);

        return ResponseEntity.ok(new AuthResponseDTO(accessToken, dto.getRefreshToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody RefreshTokenRequestDTO dto) {
        jwtService.revogarRefreshToken(dto.getRefreshToken());
        return ResponseEntity.noContent().build();
    }
}
