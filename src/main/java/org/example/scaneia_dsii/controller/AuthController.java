package org.example.scaneia_dsii.controller;

import org.example.scaneia_dsii.dtos.AuthRequestDTO;
import org.example.scaneia_dsii.dtos.AuthResponseDTO;
import org.example.scaneia_dsii.dtos.RefreshTokenRequestDTO;
import org.example.scaneia_dsii.model.Usuario;
import org.example.scaneia_dsii.model.UsuarioAcessoLogDau;
import org.example.scaneia_dsii.repository.UsuarioAcessoLogDauRepository;
import org.example.scaneia_dsii.repository.UsuarioRepository;
import org.example.scaneia_dsii.service.JwtService;
import org.example.scaneia_dsii.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioAcessoLogDauRepository usuarioAcessoLogDauRepository;

    public AuthController(UsuarioService usuarioService,
                          JwtService jwtService,
                          UsuarioRepository usuarioRepository,
                          UsuarioAcessoLogDauRepository usuarioAcessoLogDauRepository) {
        this.usuarioService = usuarioService;
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
        this.usuarioAcessoLogDauRepository = usuarioAcessoLogDauRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO dto) {
        usuarioService.validarCredenciais(dto.getUsername(), dto.getPassword());

        String accessToken = jwtService.gerarAccessToken(dto.getUsername());
        String refreshToken = jwtService.gerarRefreshToken(dto.getUsername());

        Usuario user = usuarioRepository.findByEmail(dto.getUsername());
        if (user != null) {
            UsuarioAcessoLogDau log = new UsuarioAcessoLogDau();
            log.setIdUsuario(user.getId());
            log.setIdEstrutura(user.getIdEstrutura());
            log.setDataAcesso(OffsetDateTime.now());
            usuarioAcessoLogDauRepository.save(log);
        }

        return ResponseEntity.ok(new AuthResponseDTO(accessToken, refreshToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refresh(@RequestBody RefreshTokenRequestDTO dto) {
        if (!jwtService.validarRefreshToken(dto.getRefreshToken())) {
            return ResponseEntity.status(401).build();
        }

        String username = jwtService.extrairUsernameRefreshToken(dto.getRefreshToken());
        jwtService.revogarRefreshToken(dto.getRefreshToken());

        String accessToken = jwtService.gerarAccessToken(username);
        String newRefreshToken = jwtService.gerarRefreshToken(username);

        return ResponseEntity.ok(new AuthResponseDTO(accessToken, newRefreshToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody RefreshTokenRequestDTO dto) {
        jwtService.revogarRefreshToken(dto.getRefreshToken());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/role")
    public ResponseEntity<String> filtrarRoleDoUsuario(@RequestBody RefreshTokenRequestDTO dto) {
        String role = jwtService.extrairUsuarioTipoRefreshToken(dto.getRefreshToken());
        return ResponseEntity.ok(role);
    }
}
