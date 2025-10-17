package org.example.scaneia_dsii.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.example.scaneia_dsii.dtos.UsuarioTipoRequestDTO;
import org.example.scaneia_dsii.dtos.UsuarioTipoResponseDTO;
import org.example.scaneia_dsii.validation.OnPatch;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/usuario-tipo")
public interface UsuarioTipoOpenAPI {

    @Operation(summary = "Listar todos os tipos de usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipos de usuário listados com sucesso")
    })
    @GetMapping("/selecionar")
    ResponseEntity<List<UsuarioTipoResponseDTO>> listarUsuarioTipos();

    @Operation(summary = "Buscar tipo de usuário por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado")
    })
    @GetMapping("/selecionar/{id}")
    ResponseEntity<UsuarioTipoResponseDTO> listarUsuarioTipoPorId(@PathVariable Long id);

    @Operation(summary = "Adicionar novo tipo de usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo de usuário adicionado com sucesso")
    })
    @PostMapping("/inserir")
    ResponseEntity<UsuarioTipoResponseDTO> inserirUsuarioTipo(@Valid @RequestBody UsuarioTipoRequestDTO dto);

    @Operation(summary = "Atualizar tipo de usuário existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado")
    })
    @PutMapping("/atualizar/{id}")
    ResponseEntity<UsuarioTipoResponseDTO> atualizarUsuarioTipoParcial(
            @PathVariable Long id,
            @RequestBody @Validated({OnPatch.class, Default.class}) UsuarioTipoRequestDTO request);

    @Operation(summary = "Deletar tipo de usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado")
    })
    @DeleteMapping("/excluir/{id}")
    ResponseEntity<String> deletarUsuarioTipo(@PathVariable Long id);
}
