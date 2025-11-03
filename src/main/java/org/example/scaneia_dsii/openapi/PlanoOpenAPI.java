package org.example.scaneia_dsii.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.example.scaneia_dsii.dtos.PlanoRequestDTO;
import org.example.scaneia_dsii.dtos.PlanoResponseDTO;
import org.example.scaneia_dsii.validation.OnPatch;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RequestMapping("/plano")
@Tag(name = "Plano", description = "Operações de Plano")
public interface PlanoOpenAPI {

    @Operation(summary = "Listar todos os planos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Planos listados com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso")
    })
    @GetMapping()
    ResponseEntity<List<PlanoResponseDTO>> listarPlanos();

    @Operation(summary = "Buscar plano por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plano encontrado"),
            @ApiResponse(responseCode = "404", description = "Plano não encontrado"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso")
    })
    @GetMapping("/{id}")
    ResponseEntity<PlanoResponseDTO> listarPlanoPorId(
            @Parameter(description = "ID do plano a ser buscado")
            @PathVariable Integer id
    );

    @Operation(summary = "Adicionar novo plano")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Plano adicionado com sucesso"),
            @ApiResponse(responseCode = "400", description = "O corpo da requisição está inválido"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso")
    })
    @PostMapping()
    ResponseEntity<PlanoResponseDTO> inserirPlano(
            @Valid @RequestBody PlanoRequestDTO dto
    );

    @Operation(summary = "Atualizar plano existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plano atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Plano não encontrado"),
            @ApiResponse(responseCode = "400", description = "O corpo da requisição está inválido"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso")
    })
    @PutMapping("/{id}")
    ResponseEntity<PlanoResponseDTO> atualizarPlanoParcial(
            @Parameter(description = "ID do plano a ser buscado")
            @PathVariable Integer id,
            @RequestBody @Validated({OnPatch.class, Default.class}) PlanoRequestDTO request
    );

    @Operation(summary = "Deletar plano")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plano deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Plano não encontrado"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<String> deletarPlano(
            @Parameter(description = "ID do plano a ser buscado")
            @PathVariable Integer id
    );

}
