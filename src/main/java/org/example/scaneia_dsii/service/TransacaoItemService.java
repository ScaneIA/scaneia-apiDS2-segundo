package org.example.scaneia_dsii.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.scaneia_dsii.dtos.TransacaoItemRequestDTO;
import org.example.scaneia_dsii.dtos.TransacaoItemResponseDTO;
import org.example.scaneia_dsii.model.TransacaoItem;
import org.example.scaneia_dsii.repository.TransacaoItemRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransacaoItemService {

    private final TransacaoItemRepository transacaoItemRepository;
    private final ObjectMapper objectMapper;

    public TransacaoItemService(TransacaoItemRepository transacaoItemRepository, ObjectMapper objectMapper) {
        this.transacaoItemRepository = transacaoItemRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public TransacaoItemResponseDTO inserirTransacaoItem(TransacaoItemRequestDTO request) {
        TransacaoItem novaTransacaoItem = objectMapper.convertValue(request, TransacaoItem.class);

        if (novaTransacaoItem.getQtdPlano() == null) {
            novaTransacaoItem.setQtdPlano(0);
        }
        if (novaTransacaoItem.getIdTransacao() == null) {
            novaTransacaoItem.setIdTransacao(0);
        }
        if (novaTransacaoItem.getIdPlanoEspecificao() == null) {
            novaTransacaoItem.setIdTransacao(0);
        }

        TransacaoItem salva = transacaoItemRepository.save(novaTransacaoItem);
        return objectMapper.convertValue(salva, TransacaoItemResponseDTO.class);
    }

    public List<TransacaoItemResponseDTO> listarTransacoesItens() {
        List<TransacaoItem> transacaoItens = transacaoItemRepository.findAll();
        if (transacaoItens.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma transação cadastrada no momento");
        }

        List<TransacaoItemResponseDTO> response = new ArrayList<>();
        for (TransacaoItem transacaoItem : transacaoItens) {
            response.add(objectMapper.convertValue(transacaoItem, TransacaoItemResponseDTO.class));
        }
        return response;
    }

    public TransacaoItemResponseDTO listarTransacaoItemPorId(Long id) {
        TransacaoItem transacaoItem = transacaoItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transação não encontrada"));
        return objectMapper.convertValue(transacaoItem, TransacaoItemResponseDTO.class);
    }

    @Transactional
    public TransacaoItemResponseDTO atualizarTransacaoItemParcial(Long id, TransacaoItemRequestDTO request) {
        TransacaoItem transacaoItem = transacaoItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transação Item não encontrada"));

        if (request.getQtdPlano() != null) {
            transacaoItem.setQtdPlano(request.getQtdPlano());
        }
        if (request.getIdTransacao() != null) {
            transacaoItem.setIdTransacao(request.getIdTransacao());
        }
        if (request.getIdPlanoEspecificao() != null) {
            transacaoItem.setIdPlanoEspecificao(request.getIdPlanoEspecificao());
        }


        TransacaoItem atualizada = transacaoItemRepository.save(transacaoItem);
        return objectMapper.convertValue(atualizada, TransacaoItemResponseDTO.class);
    }

    @Transactional
    public void deletarTransacaoItem(Long id) {
        if (!transacaoItemRepository.existsById(id)) {
            throw new EmptyResultDataAccessException(1);
        }
        transacaoItemRepository.deleteById(id);
    }
}
