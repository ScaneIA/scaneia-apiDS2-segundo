package org.example.scaneia_dsii.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.groups.Default;
import org.example.scaneia_dsii.dtos.UsuarioTipoRequestDTO;
import org.example.scaneia_dsii.dtos.UsuarioTipoResponseDTO;
import org.example.scaneia_dsii.service.UsuarioTipoService;
import org.example.scaneia_dsii.validation.OnCreate;
import org.example.scaneia_dsii.validation.OnPatch;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario-tipo")
public class UsuarioTipoController {

    private final UsuarioTipoService usuarioTipoService;

    public UsuarioTipoController(UsuarioTipoService usuarioTipoService) {
        this.usuarioTipoService = usuarioTipoService;
    }

    @PostMapping
    public ResponseEntity<UsuarioTipoResponseDTO> inserirUsuarioTipo(
            @RequestBody @Validated({OnCreate.class, Default.class}) UsuarioTipoRequestDTO request) {
        UsuarioTipoResponseDTO usuarioTipo = usuarioTipoService.inserirUsuarioTipo(request);
        return ResponseEntity.ok(usuarioTipo);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioTipoResponseDTO>> listarUsuarioTipos() {
        List<UsuarioTipoResponseDTO> lista = usuarioTipoService.listarUsuarioTipos();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioTipoResponseDTO> listarUsuarioTipoPorId(
            @Parameter(description = "ID do tipo de usuário a ser buscado")
            @PathVariable Long id) {
        UsuarioTipoResponseDTO usuarioTipo = usuarioTipoService.listarUsuarioTipoPorId(id);
        return ResponseEntity.ok(usuarioTipo);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioTipoResponseDTO> atualizarUsuarioTipoParcial(
            @PathVariable Long id,
            @RequestBody @Validated({OnPatch.class, Default.class}) UsuarioTipoRequestDTO request) {
        UsuarioTipoResponseDTO usuarioTipo = usuarioTipoService.atualizarUsuarioTipoParcial(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioTipo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarUsuarioTipo(@PathVariable Long id) {
        usuarioTipoService.deletarUsuarioTipo(id);
        return ResponseEntity.ok("Tipo de usuário excluído com sucesso!");
    }
}
