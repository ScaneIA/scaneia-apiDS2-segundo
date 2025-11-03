package org.example.scaneia_dsii.controller;

import jakarta.validation.Valid;
import org.example.scaneia_dsii.dtos.UsuarioPerfilResponseDTO;
import org.example.scaneia_dsii.openapi.UsuarioOpenAPI;
import org.example.scaneia_dsii.dtos.UsuarioRequestDTO;
import org.example.scaneia_dsii.dtos.UsuarioResponseDTO;
import org.example.scaneia_dsii.service.JwtService;
import org.example.scaneia_dsii.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.scaneia_dsii.model.UsuarioHierarquiaProjection;
import java.util.List;

@RestController
public class UsuarioController implements UsuarioOpenAPI {

    private final UsuarioService usuarioService;
    private final JwtService jwtService;

    public UsuarioController(UsuarioService service, JwtService jwtService) {
        this.jwtService = jwtService;
        this.usuarioService = service;
    }

    @Override
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @Override
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.listarUsuarioPorId(id));
    }

    @Override
    public ResponseEntity<UsuarioResponseDTO> criar(@Valid @RequestBody UsuarioRequestDTO request) {
        return ResponseEntity.status(201).body(usuarioService.inserirUsuario(request));
    }

    @Override
    public ResponseEntity<UsuarioResponseDTO> atualizar(Long id,
                                                        @Valid @RequestBody UsuarioRequestDTO request) {
        return ResponseEntity.ok(usuarioService.atualizarUsuarioParcial(id, request));
    }

    @Override
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hierarquia/{id}")
    public ResponseEntity<List<UsuarioHierarquiaProjection>> listarHierarquia(
            @PathVariable Long id,
            @RequestParam(defaultValue = "ABAIXO") String orientacao) {

        List<UsuarioHierarquiaProjection> resultado = usuarioService.listarHierarquia(id, orientacao);
        return ResponseEntity.ok(resultado);
    }

    @Override
    public ResponseEntity<UsuarioPerfilResponseDTO> filtrarInformacoesUsuario(@RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(usuarioService.filtrarInformacoesUsuario(authHeader));
    }

    @PostMapping("/perfil")
    public ResponseEntity<UsuarioPerfilResponseDTO> getPerfil(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String username = jwtService.extrairUsername(token); // chama AQUI
        return ResponseEntity.ok(usuarioService.filtrarInformacoesUsuario(username));
    }


}
