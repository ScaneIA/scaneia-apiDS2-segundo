package org.example.scaneia_dsii.openapi;

import jakarta.validation.groups.Default;
import org.example.scaneia_dsii.dtos.EstruturaTipoRequestDTO;
import org.example.scaneia_dsii.dtos.EstruturaTipoResponseDTO;
import org.example.scaneia_dsii.validation.OnCreate;
import org.example.scaneia_dsii.validation.OnPatch;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/estruturaTipo")
public interface EstruturaTipoOpenAPI {

    @PostMapping()
    ResponseEntity<EstruturaTipoResponseDTO> inserirEstruturaTipo(@RequestBody @Validated({OnCreate.class, Default.class}) EstruturaTipoRequestDTO request) throws ConstraintViolationException;

    @GetMapping()
    ResponseEntity<List<EstruturaTipoResponseDTO>> listarEstruturasTipo();

    @GetMapping("/{id}")
    ResponseEntity<EstruturaTipoResponseDTO> listarEstruturaTipoPorId(@PathVariable Integer id);

    @PutMapping("/{id}")
    ResponseEntity<EstruturaTipoResponseDTO> atualizarEstruturaTipoParcial(@PathVariable Integer id, @RequestBody @Validated({OnPatch.class, Default.class}) EstruturaTipoRequestDTO request);

    @DeleteMapping("/{id}")
    ResponseEntity<String> deletarEstruturaTipo(@PathVariable Integer id);
}
