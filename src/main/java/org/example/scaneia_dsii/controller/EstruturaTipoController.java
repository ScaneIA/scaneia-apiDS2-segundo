package org.example.scaneia_dsii.controller;

import jakarta.validation.groups.Default;
import org.example.scaneia_dsii.dtos.EstruturaTipoRequestDTO;
import org.example.scaneia_dsii.dtos.EstruturaTipoResponseDTO;
import org.example.scaneia_dsii.openapi.EstruturaTipoOpenAPI;
import org.example.scaneia_dsii.service.EstruturaTipoService;
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
public class EstruturaTipoController implements EstruturaTipoOpenAPI {

    private final EstruturaTipoService estruturaTipoService;

    public EstruturaTipoController(EstruturaTipoService estruturaTipoService) {
        this.estruturaTipoService = estruturaTipoService;
    }

    @Override
    public ResponseEntity<EstruturaTipoResponseDTO> inserirEstruturaTipo(@RequestBody @Validated({OnCreate.class, Default.class}) EstruturaTipoRequestDTO request) throws ConstraintViolationException {
        EstruturaTipoResponseDTO estruturaTipo = estruturaTipoService.inserirEstruturaTipo(request);
        return ResponseEntity.ok(estruturaTipo);
    }

    @Override
    public ResponseEntity<List<EstruturaTipoResponseDTO>> listarEstruturasTipo(){
        List<EstruturaTipoResponseDTO> lista = estruturaTipoService.listarEstruturasTipo();
        return ResponseEntity.ok(lista);
    }

    @Override
    public ResponseEntity<EstruturaTipoResponseDTO> listarEstruturaTipoPorId(@PathVariable Integer id){
        EstruturaTipoResponseDTO estruturaTipo = estruturaTipoService.listarEstruturaTipoPorId(id);
        return ResponseEntity.ok(estruturaTipo);
    }

    @Override
    public ResponseEntity<EstruturaTipoResponseDTO> atualizarEstruturaTipoParcial(@PathVariable Integer id, @RequestBody @Validated({OnPatch.class, Default.class}) EstruturaTipoRequestDTO request){
        EstruturaTipoResponseDTO estruturaTipo = estruturaTipoService.atualizarEstruturaTipoParcial(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(estruturaTipo);
    }
    @Override
    public ResponseEntity<String> deletarEstruturaTipo(@PathVariable Integer id){
        estruturaTipoService.deletarEstruturaTipo(id);
        return ResponseEntity.ok("Tipo de estrutura excluído com sucesso!");
    }
}
