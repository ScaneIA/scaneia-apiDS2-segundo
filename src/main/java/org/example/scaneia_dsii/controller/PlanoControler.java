package org.example.scaneia_dsii.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.groups.Default;
import org.example.scaneia_dsii.dtos.PlanoRequestDTO;
import org.example.scaneia_dsii.dtos.PlanoResponseDTO;
import org.example.scaneia_dsii.service.PlanoService;
import org.example.scaneia_dsii.validation.OnCreate;
import org.example.scaneia_dsii.validation.OnPatch;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plano")
public class PlanoControler {
    private final PlanoService planoService;

    public PlanoControler(PlanoService planoService) {
        this.planoService = planoService;
    }

    @PostMapping("/inserir")
    public ResponseEntity<PlanoResponseDTO> inserirPlano(@RequestBody @Validated({OnCreate.class, Default.class}) PlanoRequestDTO request) throws ConstraintViolationException {
        PlanoResponseDTO plano = planoService.inserirPlano(request);
        return ResponseEntity.ok(plano);
    }
    @GetMapping("/selecionar")
    public ResponseEntity<List<PlanoResponseDTO>> listarPlanos(){
        List<PlanoResponseDTO> lista = planoService.listarPlanos();
        return ResponseEntity.ok(lista);
    }
    @GetMapping("/selecionar/{id}")
    public ResponseEntity<PlanoResponseDTO> listarPlanosPorId(
            @Parameter(description = "ID do produto a ser buscado")
            @PathVariable Integer id){
        PlanoResponseDTO plano = planoService.listarPlanoPorId(id);
        return ResponseEntity.ok(plano);
    }
    @PatchMapping("/atualizarParcial/{id}")
    public ResponseEntity<PlanoResponseDTO> atualizarPlanoParcial(@PathVariable Integer id, @RequestBody @Validated({OnPatch.class, Default.class}) PlanoRequestDTO request){
        PlanoResponseDTO plano = planoService.atualizarPlanoParcial(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(plano);
    }
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<String> deletarProduto(@PathVariable Integer id){
        planoService.deletarPlano(id);
        return ResponseEntity.ok("Plano excluído com sucesso!");
    }
}
