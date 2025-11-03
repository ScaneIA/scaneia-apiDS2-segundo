package org.example.scaneia_dsii.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.groups.Default;
import org.example.scaneia_dsii.dtos.UsuarioTipoRequestDTO;
import org.example.scaneia_dsii.dtos.UsuarioTipoResponseDTO;
import org.example.scaneia_dsii.validation.OnCreate;
import org.example.scaneia_dsii.validation.OnPatch;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
@RequestMapping("/usuarioTipo")
@Tag(name = "Usuário Tipo", description = "Operações de Usuário Tipo")
public interface UsuarioTipoOpenAPI {
    @Operation(summary = "Listar todos os tipos de usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipos de usuário listados com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso")
    })
    @GetMapping()
    ResponseEntity<List<UsuarioTipoResponseDTO>> listarUsuarioTipos();

    @Operation(summary = "Buscar tipo de usuário por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso")
    })
    @GetMapping("/{id}")
    ResponseEntity<UsuarioTipoResponseDTO> listarUsuarioTipoPorId(
            @Parameter(description = "ID do tipo de usuário a ser buscado")
            @PathVariable Long id
    );

    @Operation(summary = "Adicionar novo tipo de usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo de usuário adicionado com sucesso"),
            @ApiResponse(responseCode = "400", description = "O corpo da requisição está inválido"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso")
    })
    @PostMapping()
    ResponseEntity<UsuarioTipoResponseDTO> inserirUsuarioTipo(
            @RequestBody @Validated({OnCreate.class, Default.class}) UsuarioTipoRequestDTO request
    );

    @Operation(summary = "Atualizar tipo de usuário existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado"),
            @ApiResponse(responseCode = "400", description = "O corpo da requisição está inválido"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso")
    })
    @PutMapping("/{id}")
    ResponseEntity<UsuarioTipoResponseDTO> atualizarUsuarioTipoParcial(
            @Parameter(description = "ID do tipo de usuário a ser buscado")
            @PathVariable Long id,
            @RequestBody @Validated({OnPatch.class, Default.class}) UsuarioTipoRequestDTO request
    );

    @Operation(summary = "Deletar tipo de usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<String> deletarUsuarioTipo(
            @Parameter(description = "ID do tipo de usuário a ser buscado")
            @PathVariable Long id
    );









}
