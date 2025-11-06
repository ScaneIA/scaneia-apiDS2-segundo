package org.example.scaneia_dsii.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.example.scaneia_dsii.dtos.TransacaoItemRequestDTO;
import org.example.scaneia_dsii.dtos.TransacaoItemResponseDTO;
import org.example.scaneia_dsii.validation.OnCreate;
import org.example.scaneia_dsii.validation.OnPatch;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/transacaoItem")
@Tag(name = "Transação Item", description = "Operações de Transação Item")
public interface TransacaoItemOpenAPI {

    @Operation(summary = "Listar todos os itens de transação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Itens de transação listados com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso")
    })
    @GetMapping()
    ResponseEntity<List<TransacaoItemResponseDTO>> listarTransacoesItens();

    @Operation(summary = "Buscar item de transação por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item encontrado"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso")
    })
    @GetMapping("/{id}")
    ResponseEntity<TransacaoItemResponseDTO> listarTransacaoItemPorId(
            @Parameter(description = "ID do item da transação a ser buscada")
            @PathVariable Long id);

    @Operation(summary = "Adicionar novo item de transação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item de transação adicionado com sucesso"),
            @ApiResponse(responseCode = "400", description = "O corpo da requisição está inválido"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso")
    })
    @PostMapping()
    ResponseEntity<TransacaoItemResponseDTO> inserirTransacaoItem(
            @RequestBody @Validated({OnCreate.class, Default.class}) TransacaoItemRequestDTO request
    );

    @Operation(summary = "Atualizar item de transação existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item de transação atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item de transação não encontrado"),
            @ApiResponse(responseCode = "400", description = "O corpo da requisição está inválido"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso")
    })
    @PutMapping("/{id}")
    ResponseEntity<TransacaoItemResponseDTO> atualizarTransacaoItemParcial(
            @Parameter(description = "ID do item da transação a ser buscada")
            @PathVariable Long id,
            @RequestBody @Validated({OnPatch.class, Default.class}) TransacaoItemRequestDTO request
    );

    @Operation(summary = "Deletar item de transação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item de transação deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item de transação não encontrado"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<String> deletarTransacaoItem(
            @Parameter(description = "ID do item da transação a ser buscada")
            @PathVariable Long id
    );









}
