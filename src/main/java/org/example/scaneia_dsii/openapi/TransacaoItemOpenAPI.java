package org.example.scaneia_dsii.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.example.scaneia_dsii.dtos.TransacaoItemRequestDTO;
import org.example.scaneia_dsii.dtos.TransacaoItemResponseDTO;
import org.example.scaneia_dsii.validation.OnPatch;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/transacao-item")
public interface TransacaoItemOpenAPI {

    @Operation(summary = "Listar todos os itens de transação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Itens de transação listados com sucesso")
    })
    @GetMapping("/selecionar")
    ResponseEntity<List<TransacaoItemResponseDTO>> listarItensTransacao();

    @Operation(summary = "Buscar item de transação por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item encontrado"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    @GetMapping("/selecionar/{id}")
    ResponseEntity<TransacaoItemResponseDTO> listarItemTransacaoPorId(@PathVariable Integer id);

    @Operation(summary = "Adicionar novo item de transação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item de transação adicionado com sucesso")
    })
    @PostMapping("/inserir")
    ResponseEntity<TransacaoItemResponseDTO> inserirItemTransacao(@Valid @RequestBody TransacaoItemRequestDTO dto);

    @Operation(summary = "Atualizar item de transação existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item de transação atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item de transação não encontrado")
    })
    @PutMapping("/atualizar/{id}")
    ResponseEntity<TransacaoItemResponseDTO> atualizarItemTransacao(
            @PathVariable Integer id,
            @RequestBody @Validated({OnPatch.class, Default.class}) TransacaoItemRequestDTO request);

    @Operation(summary = "Deletar item de transação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item de transação deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item de transação não encontrado")
    })
    @DeleteMapping("/excluir/{id}")
    ResponseEntity<String> deletarItemTransacao(@PathVariable Integer id);
}
