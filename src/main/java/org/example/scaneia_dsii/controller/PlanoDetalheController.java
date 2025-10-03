package org.example.scaneia_dsii.controller;

import jakarta.validation.groups.Default;
import org.example.scaneia_dsii.dtos.PlanoDetalheRequestDTO;
import org.example.scaneia_dsii.dtos.PlanoDetalheResponseDTO;
import org.example.scaneia_dsii.openapi.PlanoDetalheOpenAPI;
import org.example.scaneia_dsii.service.PlanoDetalheService;
import org.example.scaneia_dsii.validation.OnCreate;
import org.example.scaneia_dsii.validation.OnPatch;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class PlanoDetalheController implements PlanoDetalheOpenAPI {
    private final PlanoDetalheService planoDetalheService;

    public PlanoDetalheController(PlanoDetalheService planoDetalheService) {
        this.planoDetalheService = planoDetalheService;
    }

    @Override
    public ResponseEntity<PlanoDetalheResponseDTO> inserirPlanoDetalhe(@RequestBody @Validated({OnCreate.class, Default.class}) PlanoDetalheRequestDTO request) throws ConstraintViolationException {
        PlanoDetalheResponseDTO planoDetalhe = planoDetalheService.inserirPlanoDetalhe(request);
        return ResponseEntity.ok(planoDetalhe);
    }

    @Override
    public ResponseEntity<List<PlanoDetalheResponseDTO>> listarPlanosDetalhes(){
        List<PlanoDetalheResponseDTO> lista = planoDetalheService.listarPlanosDetalhes();
        return ResponseEntity.ok(lista);
    }

    @Override
    public ResponseEntity<PlanoDetalheResponseDTO> listarPlanoDetalhePorId(@PathVariable Long id){
        PlanoDetalheResponseDTO planoDetalhe = planoDetalheService.listarPlanoDetalhePorId(id);
        return ResponseEntity.ok(planoDetalhe);
    }

    @Override
    public ResponseEntity<PlanoDetalheResponseDTO> atualizarPlanoDetalheParcial(@PathVariable Long id, @RequestBody @Validated({OnPatch.class, Default.class}) PlanoDetalheRequestDTO request){
        PlanoDetalheResponseDTO planoDetalhe = planoDetalheService.atualizarPlanoDetalheParcial(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(planoDetalhe);
    }
    @Override
    public ResponseEntity<String> deletarPlanoDetalhe(@PathVariable Long id){
        planoDetalheService.deletarPlanoDetalhe(id);
        return ResponseEntity.ok("Plano detalhe excluído com sucesso!");
    }

}
