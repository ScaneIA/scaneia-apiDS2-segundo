package org.example.scaneia_dsii.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.example.scaneia_dsii.dtos.UsuarioRequestDTO;
import org.example.scaneia_dsii.dtos.UsuarioResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/usuario")
public interface UsuarioOpenAPI {

    @Operation(summary = "Listar todos os usuários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuários listados com sucesso")
    })
    @GetMapping("/selecionar")
    ResponseEntity<List<UsuarioResponseDTO>> listar();

    @Operation(summary = "Buscar usuário por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/selecionar/{id}")
    ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id);

    @Operation(summary = "Criar novo usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso")
    })
    @PostMapping("/inserir")
    ResponseEntity<UsuarioResponseDTO> criar(@Valid @RequestBody UsuarioRequestDTO dto);

    @Operation(summary = "Atualizar usuário existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PutMapping("/atualizar/{id}")
    ResponseEntity<UsuarioResponseDTO> atualizar(@PathVariable Long id,
                                                 @Valid @RequestBody UsuarioRequestDTO dto);

    @Operation(summary = "Deletar usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @DeleteMapping("/excluir/{id}")
    ResponseEntity<String> deletar(@PathVariable Long id);
}
