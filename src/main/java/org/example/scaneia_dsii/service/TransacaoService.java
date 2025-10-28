package org.example.scaneia_dsii.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.scaneia_dsii.dtos.TransacaoRequestDTO;
import org.example.scaneia_dsii.dtos.TransacaoResponseDTO;
import org.example.scaneia_dsii.model.Transacao;
import org.example.scaneia_dsii.repository.TransacaoRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final ObjectMapper objectMapper;

    public TransacaoService(TransacaoRepository transacaoRepository, ObjectMapper objectMapper) {
        this.transacaoRepository = transacaoRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public TransacaoResponseDTO inserirTransacao(TransacaoRequestDTO request) {
        Transacao novaTransacao = objectMapper.convertValue(request, Transacao.class);

        if (novaTransacao.getValorTotal() == null) {
            novaTransacao.setValorTotal(0.0); // Double
        }
        if (novaTransacao.getEstrutura_id() == null) {
            novaTransacao.setEstrutura_id(0); // Integer
        }
        if (novaTransacao.getDataCriacao() == null) {
            novaTransacao.setDataCriacao(new java.util.Date()); // Date
        }
        if (novaTransacao.getDataAlteracao() == null) {
            novaTransacao.setDataAlteracao(new java.util.Date()); // Date
        }

        Transacao salva = transacaoRepository.save(novaTransacao);
        return objectMapper.convertValue(salva, TransacaoResponseDTO.class);
    }

    public List<TransacaoResponseDTO> listarTransacoes() {
        List<Transacao> transacoes = transacaoRepository.findAll();
        if (transacoes.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma transação cadastrada no momento");
        }

        List<TransacaoResponseDTO> response = new ArrayList<>();
        for (Transacao transacao : transacoes) {
            response.add(objectMapper.convertValue(transacao, TransacaoResponseDTO.class));
        }
        return response;
    }

    public TransacaoResponseDTO listarTransacaoPorId(Long id) {
        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transação não encontrada"));
        return objectMapper.convertValue(transacao, TransacaoResponseDTO.class);
    }

    @Transactional
    public TransacaoResponseDTO atualizarTransacaoParcial(Long id, TransacaoRequestDTO request) {
        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transação não encontrada"));

        if (request.getValorTotal() != null) {
            transacao.setValorTotal(request.getValorTotal());
        }
        if (request.getEstrutura_id() != null) {
            transacao.setEstrutura_id(request.getEstrutura_id());
        }
        if (request.getDataCriacao() != null) {
            transacao.setDataCriacao(request.getDataCriacao());
        }
        if (request.getDataAlteracao() != null) {
            transacao.setDataAlteracao(request.getDataAlteracao());
        }

        Transacao atualizada = transacaoRepository.save(transacao);
        return objectMapper.convertValue(atualizada, TransacaoResponseDTO.class);
    }

    @Transactional
    public void deletarTransacao(Long id) {
        if (!transacaoRepository.existsById(id)) {
            throw new EmptyResultDataAccessException(1);
        }
        transacaoRepository.deleteById(id);
    }
}
