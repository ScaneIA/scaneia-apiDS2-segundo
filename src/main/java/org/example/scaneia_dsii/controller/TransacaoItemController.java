package org.example.scaneia_dsii.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.groups.Default;
import org.example.scaneia_dsii.dtos.TransacaoItemRequestDTO;
import org.example.scaneia_dsii.dtos.TransacaoItemResponseDTO;
import org.example.scaneia_dsii.service.TransacaoItemService;
import org.example.scaneia_dsii.validation.OnCreate;
import org.example.scaneia_dsii.validation.OnPatch;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacao-item")
public class TransacaoItemController {

    private final TransacaoItemService transacaoItemService;

    public TransacaoItemController(TransacaoItemService transacaoItemService) {
        this.transacaoItemService = transacaoItemService;
    }

    @PostMapping
    public ResponseEntity<TransacaoItemResponseDTO> inserirTransacaoItem(
            @RequestBody @Validated({OnCreate.class, Default.class}) TransacaoItemRequestDTO request) {
        TransacaoItemResponseDTO item = transacaoItemService.inserirTransacaoItem(request);
        return ResponseEntity.ok(item);
    }

    @GetMapping
    public ResponseEntity<List<TransacaoItemResponseDTO>> listarTransacoesItens() {
        List<TransacaoItemResponseDTO> lista = transacaoItemService.listarTransacoesItens();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransacaoItemResponseDTO> listarTransacaoItemPorId(
            @Parameter(description = "ID do item da transação a ser buscado")
            @PathVariable Long id) {
        TransacaoItemResponseDTO item = transacaoItemService.listarTransacaoItemPorId(id);
        return ResponseEntity.ok(item);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TransacaoItemResponseDTO> atualizarTransacaoItemParcial(
            @PathVariable Long id,
            @RequestBody @Validated({OnPatch.class, Default.class}) TransacaoItemRequestDTO request) {
        TransacaoItemResponseDTO item = transacaoItemService.atualizarTransacaoItemParcial(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(item);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarTransacaoItem(@PathVariable Long id) {
        transacaoItemService.deletarTransacaoItem(id);
        return ResponseEntity.ok("Item da transação excluído com sucesso!");
    }
}
