package org.example.scaneia_dsii.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.groups.Default;
import org.example.scaneia_dsii.dtos.EstruturaRequestDTO;
import org.example.scaneia_dsii.dtos.EstruturaResponseDTO;
import org.example.scaneia_dsii.openapi.EstruturaOpenAPI;
import org.example.scaneia_dsii.service.EstruturaService;
import org.example.scaneia_dsii.validation.OnCreate;
import org.example.scaneia_dsii.validation.OnPatch;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
public class EstruturaController implements EstruturaOpenAPI {

    private final EstruturaService estruturaService;

    public EstruturaController(EstruturaService estruturaService) {
        this.estruturaService = estruturaService;
    }

    @Override
    public ResponseEntity<EstruturaResponseDTO> inserirEstrutura(
            @RequestBody @Validated({OnCreate.class, Default.class}) EstruturaRequestDTO request) {
        EstruturaResponseDTO estrutura = estruturaService.inserirEstrutura(request);
        return ResponseEntity.ok(estrutura);
    }

    @Override
    public ResponseEntity<List<EstruturaResponseDTO>> listarEstruturas() {
        List<EstruturaResponseDTO> lista = estruturaService.listarEstruturas();
        return ResponseEntity.ok(lista);
    }

    @Override
    public ResponseEntity<EstruturaResponseDTO> listarEstruturaPorId(
            @Parameter(description = "ID da estrutura a ser buscada")
            @PathVariable Long id) {
        EstruturaResponseDTO estrutura = estruturaService.listarEstruturaPorId(id);
        return ResponseEntity.ok(estrutura);
    }

    @Override
    public ResponseEntity<EstruturaResponseDTO> atualizarEstruturaParcial(
            @PathVariable Long id,
            @RequestBody @Validated({OnPatch.class, Default.class}) EstruturaRequestDTO request) {
        EstruturaResponseDTO estrutura = estruturaService.atualizarEstruturaParcial(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(estrutura);
    }

    @Override
    public ResponseEntity<String> deletarEstrutura(@PathVariable Long id) {
        estruturaService.deletarEstrutura(id);
        return ResponseEntity.ok("Estrutura excluída com sucesso!");
    }
}
