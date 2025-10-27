package org.example.scaneia_dsii.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.example.scaneia_dsii.dtos.PlanoDetalheRequestDTO;
import org.example.scaneia_dsii.dtos.PlanoDetalheResponseDTO;
import org.example.scaneia_dsii.validation.OnPatch;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RequestMapping("/planoDetalhe")
public interface PlanoDetalheOpenAPI {


    @Operation(summary = "Listar todos os planos detalhes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Planos detalhes listados com sucesso")
    })
    @GetMapping()
    ResponseEntity<List<PlanoDetalheResponseDTO>> listarPlanosDetalhes();

    @Operation(summary = "Buscar plano detalhe por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plano detalhe encontrado"),
            @ApiResponse(responseCode = "404", description = "Plano detalhe não encontrado")
    })
    @GetMapping("/{id}")
    ResponseEntity<PlanoDetalheResponseDTO> listarPlanoDetalhePorId(@PathVariable Long id);

    @Operation(summary = "Adicionar novo plano detalhe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Plano detalhe adicionado com sucesso")
    })
    @PostMapping()
    ResponseEntity<PlanoDetalheResponseDTO> inserirPlanoDetalhe(@Valid @RequestBody PlanoDetalheRequestDTO request);

    @Operation(summary = "Atualizar plano detalhe existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plano detalhe atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Plano detalhe não encontrado")
    })

    @PutMapping("/{id}")
    ResponseEntity<PlanoDetalheResponseDTO> atualizarPlanoDetalheParcial(@PathVariable Long id,
                                                           @RequestBody @Validated({OnPatch.class, Default.class}) PlanoDetalheRequestDTO request);

    @Operation(summary = "Deletar plano detalhe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plano detalhe deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Plano detalhe não encontrado")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<String> deletarPlanoDetalhe(@PathVariable Long id);

}
