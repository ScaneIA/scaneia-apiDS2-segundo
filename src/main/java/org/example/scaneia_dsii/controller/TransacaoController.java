package org.example.scaneia_dsii.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.groups.Default;
import org.example.scaneia_dsii.dtos.TransacaoRequestDTO;
import org.example.scaneia_dsii.dtos.TransacaoResponseDTO;
import org.example.scaneia_dsii.service.TransacaoService;
import org.example.scaneia_dsii.validation.OnCreate;
import org.example.scaneia_dsii.validation.OnPatch;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacao")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping
    public ResponseEntity<TransacaoResponseDTO> inserirTransacao(
            @RequestBody @Validated({OnCreate.class, Default.class}) TransacaoRequestDTO request) {
        TransacaoResponseDTO transacao = transacaoService.inserirTransacao(request);
        return ResponseEntity.ok(transacao);
    }

    @GetMapping
    public ResponseEntity<List<TransacaoResponseDTO>> listarTransacoes() {
        List<TransacaoResponseDTO> lista = transacaoService.listarTransacoes();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransacaoResponseDTO> listarTransacaoPorId(
            @Parameter(description = "ID da transação a ser buscada")
            @PathVariable Long id) {
        TransacaoResponseDTO transacao = transacaoService.listarTransacaoPorId(id);
        return ResponseEntity.ok(transacao);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TransacaoResponseDTO> atualizarTransacaoParcial(
            @PathVariable Long id,
            @RequestBody @Validated({OnPatch.class, Default.class}) TransacaoRequestDTO request) {
        TransacaoResponseDTO transacao = transacaoService.atualizarTransacaoParcial(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(transacao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarTransacao(@PathVariable Long id) {
        transacaoService.deletarTransacao(id);
        return ResponseEntity.ok("Transação excluída com sucesso!");
    }
}
