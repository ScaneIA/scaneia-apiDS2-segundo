package org.example.scaneia_dsii.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
public interface PlanoOpenAPI {

    @Operation(summary = "Listar todos os planos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Planos listados com sucesso")
    })
    @GetMapping()
    ResponseEntity<List<PlanoResponseDTO>> listarPlanos();

    @Operation(summary = "Buscar plano por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plano encontrado"),
            @ApiResponse(responseCode = "404", description = "Plano não encontrado")
    })
    @GetMapping("/{id}")
    ResponseEntity<PlanoResponseDTO> listarPlanoPorId(@PathVariable Integer id);

    @Operation(summary = "Adicionar novo plano")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Plano adicionado com sucesso")
    })
    @PostMapping()
    ResponseEntity<PlanoResponseDTO> inserirPlano(@Valid @RequestBody PlanoRequestDTO dto);

    @Operation(summary = "Atualizar plano existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plano atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Plano não encontrado")
    })

    @PutMapping("/{id}")
    ResponseEntity<PlanoResponseDTO> atualizarPlanoParcial(@PathVariable Integer id,
                                                 @RequestBody @Validated({OnPatch.class, Default.class}) PlanoRequestDTO request);

    @Operation(summary = "Deletar plano")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plano deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Plano não encontrado")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<String> deletarPlano(@PathVariable Integer id);

}
