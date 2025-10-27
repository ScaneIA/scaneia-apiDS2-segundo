package org.example.scaneia_dsii.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.example.scaneia_dsii.dtos.TransacaoRequestDTO;
import org.example.scaneia_dsii.dtos.TransacaoResponseDTO;
import org.example.scaneia_dsii.validation.OnPatch;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RequestMapping("/transacao")
public interface TransacaoOpenAPI {

    @Operation(summary = "Listar todas as transações")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transações listadas com sucesso")
    })
    @GetMapping()
    ResponseEntity<List<TransacaoResponseDTO>> listarTransacoes();

    @Operation(summary = "Buscar transação por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transação encontrada"),
            @ApiResponse(responseCode = "404", description = "Transação não encontrada")
    })
    @GetMapping("/{id}")
    ResponseEntity<TransacaoResponseDTO> listarTransacaoPorId(
            @Parameter(description = "ID da transação a ser buscada")
            @PathVariable Long id);

    @Operation(summary = "Adicionar nova transação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transação adicionada com sucesso")
    })
    @PostMapping()
    ResponseEntity<TransacaoResponseDTO> inserirTransacao(@Valid @RequestBody TransacaoRequestDTO dto);

    @Operation(summary = "Atualizar transação existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transação atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Transação não encontrada")
    })
    @PutMapping("/{id}")
    ResponseEntity<TransacaoResponseDTO> atualizarTransacaoParcial(
            @PathVariable Long id,
            @RequestBody @Validated({OnPatch.class, Default.class}) TransacaoRequestDTO request);

    @Operation(summary = "Deletar transação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transação deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Transação não encontrada")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<String> deletarTransacao(@PathVariable Long id);

}
