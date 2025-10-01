package org.example.scaneia_dsii.crud_usuario.controller;

import jakarta.validation.Valid;
import org.example.scaneia_dsii.crud_usuario.UsuarioOpenAPI;
import org.example.scaneia_dsii.crud_usuario.DTO.UsuarioRequestDTO;
import org.example.scaneia_dsii.crud_usuario.DTO.UsuarioResponseDTO;
import org.example.scaneia_dsii.crud_usuario.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController implements UsuarioOpenAPI {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @Override
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Override
    public ResponseEntity<UsuarioResponseDTO> criar(@Valid @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.status(201).body(service.salvar(dto));
    }

    @Override
    public ResponseEntity<UsuarioResponseDTO> atualizar(Long id,
                                                        @Valid @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @Override
    public ResponseEntity<Void> deletar(Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
