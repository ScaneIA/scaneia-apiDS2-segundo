package org.example.scaneia_dsii.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
public interface TransacaoItemOpenAPI {

    @Operation(summary = "Listar todos os itens de transação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Itens de transação listados com sucesso")
    })
    @GetMapping()
    ResponseEntity<List<TransacaoItemResponseDTO>> listarTransacoesItens();

    @Operation(summary = "Buscar item de transação por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item encontrado"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    @GetMapping("/{id}")
    ResponseEntity<TransacaoItemResponseDTO> listarTransacaoItemPorId(
            @Parameter(description = "ID do item da transação a ser buscado")
            @PathVariable Long id);

    @Operation(summary = "Adicionar novo item de transação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item de transação adicionado com sucesso")
    })
    @PostMapping()
    ResponseEntity<TransacaoItemResponseDTO> inserirTransacaoItem(
            @RequestBody @Validated({OnCreate.class, Default.class}) TransacaoItemRequestDTO request);
    @Operation(summary = "Atualizar item de transação existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item de transação atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item de transação não encontrado")
    })
    @PutMapping("/{id}")
    ResponseEntity<TransacaoItemResponseDTO> atualizarTransacaoItemParcial(
            @PathVariable Long id,
            @RequestBody @Validated({OnPatch.class, Default.class}) TransacaoItemRequestDTO request);

    @Operation(summary = "Deletar item de transação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item de transação deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item de transação não encontrado")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<String> deletarTransacaoItem(@PathVariable Long id);









}
