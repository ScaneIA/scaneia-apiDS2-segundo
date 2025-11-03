package org.example.scaneia_dsii.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.scaneia_dsii.dtos.UsuarioPerfilResponseDTO;
import org.example.scaneia_dsii.dtos.UsuarioRequestDTO;
import org.example.scaneia_dsii.dtos.UsuarioResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/usuarios")
@Tag(name = "Usuário", description = "Operações de Usuário")
public interface UsuarioOpenAPI {

    @Operation(summary = "Listar todos os usuários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuários listados com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso")
    })
    @GetMapping
    ResponseEntity<List<UsuarioResponseDTO>> listar();

    @Operation(summary = "Buscar usuário por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso")
    })
    @GetMapping("/{id}")
    ResponseEntity<UsuarioResponseDTO> buscarPorId(
            @Parameter(description = "ID do usuário a ser buscado")
            @PathVariable Long id
    );

    @Operation(summary = "Criar novo usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "O corpo da requisição está inválido"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso")
    })
    @PostMapping
    ResponseEntity<UsuarioResponseDTO> criar(
            @Valid @RequestBody UsuarioRequestDTO dto
    );

    @Operation(summary = "Atualizar usuário existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "400", description = "O corpo da requisição está inválido"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso")
    })
    @PutMapping("/{id}")
    ResponseEntity<UsuarioResponseDTO> atualizar(
            @Parameter(description = "ID do usuário a ser buscado")
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO dto);

    @Operation(summary = "Deletar usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deletar(
            @Parameter(description = "ID do usuário a ser buscado")
            @PathVariable Long id
    );

    @Operation(summary = "Filtrar informações do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Informações obtidas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
    })
    @GetMapping("/filtro")
    ResponseEntity<UsuarioPerfilResponseDTO> filtrarInformacoesUsuario(
            @RequestHeader("Authorization") String authHeader
    );
}
