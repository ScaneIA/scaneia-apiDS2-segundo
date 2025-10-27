package org.example.scaneia_dsii.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.example.scaneia_dsii.dtos.EstruturaRequestDTO;
import org.example.scaneia_dsii.dtos.EstruturaResponseDTO;
import org.example.scaneia_dsii.validation.OnPatch;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RequestMapping("/estrutura")
public interface EstruturaOpenAPI {

    @Operation(summary = "Listar todas as estruturas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estruturas listadas com sucesso")
    })
    @GetMapping()
    ResponseEntity<List<EstruturaResponseDTO>> listarEstruturas();

    @Operation(summary = "Buscar estrutura por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estrutura encontrada"),
            @ApiResponse(responseCode = "404", description = "Estrutura não encontrada")
    })
    @GetMapping("/{id}")
    ResponseEntity<EstruturaResponseDTO> listarEstruturaPorId(
            @Parameter(description = "ID da estrutura a ser buscada")
            @PathVariable Long id);

    @Operation(summary = "Adicionar nova estrutura")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estrutura adicionada com sucesso")
    })
    @PostMapping()
    ResponseEntity<EstruturaResponseDTO> inserirEstrutura(@Valid @RequestBody EstruturaRequestDTO dto);

    @Operation(summary = "Atualizar estrutura existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estrutura atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estrutura não encontrada")
    })
    @PutMapping("/{id}")
    ResponseEntity<EstruturaResponseDTO> atualizarEstruturaParcial(
            @PathVariable Long id,
            @RequestBody @Validated({OnPatch.class, Default.class}) EstruturaRequestDTO request);

    @Operation(summary = "Deletar estrutura")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estrutura deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estrutura não encontrada")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<String> deletarEstrutura(@PathVariable Long id);

}
