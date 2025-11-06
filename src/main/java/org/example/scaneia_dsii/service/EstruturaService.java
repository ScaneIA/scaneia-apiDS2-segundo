package org.example.scaneia_dsii.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.scaneia_dsii.dtos.EstruturaRequestDTO;
import org.example.scaneia_dsii.dtos.EstruturaResponseDTO;
import org.example.scaneia_dsii.model.Estrutura;
import org.example.scaneia_dsii.repository.EstruturaRepository;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EstruturaService {

    private final EstruturaRepository estruturaRepository;
    private final ObjectMapper objectMapper;

    public EstruturaService(EstruturaRepository estruturaRepository, ObjectMapper objectMapper) {
        this.estruturaRepository = estruturaRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public EstruturaResponseDTO inserirEstrutura(EstruturaRequestDTO request) {
        Estrutura novaEstrutura = objectMapper.convertValue(request, Estrutura.class);

        if (novaEstrutura.getDescricao() == null) {
            novaEstrutura.setDescricao("");
        }
        if (novaEstrutura.getEstruturaId() == null) {
            novaEstrutura.setEstruturaId(0);
        }
        if (novaEstrutura.getEstruturaTipoId() == null) {
            novaEstrutura.setEstruturaTipoId(0);
        }
        if (novaEstrutura.getDataCriacao() == null) {
            novaEstrutura.setDataCriacao(new Date());
        }
        if (novaEstrutura.getDataAlteracao() == null) {
            novaEstrutura.setDataAlteracao(new Date());
        }
        if (novaEstrutura.getAtivo() == null) {
            novaEstrutura.setAtivo(false);
        }

        Estrutura salva = estruturaRepository.save(novaEstrutura);
        return objectMapper.convertValue(salva, EstruturaResponseDTO.class);
    }

    public List<EstruturaResponseDTO> listarEstruturas() {
        List<Estrutura> estruturas = estruturaRepository.findAll();
        if (estruturas.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma estrutura cadastrada no momento");
        }

        List<EstruturaResponseDTO> response = new ArrayList<>();
        for (Estrutura estrutura : estruturas) {
            response.add(objectMapper.convertValue(estrutura, EstruturaResponseDTO.class));
        }
        return response;
    }

    public EstruturaResponseDTO listarEstruturaPorId(Long id) {
        Estrutura estrutura = estruturaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estrutura não encontrada"));
        return objectMapper.convertValue(estrutura, EstruturaResponseDTO.class);
    }

    @Transactional
    public EstruturaResponseDTO atualizarEstruturaParcial(Long id, EstruturaRequestDTO request) {
        Estrutura estrutura = estruturaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estrutura não encontrada"));

        if (request.getDescricao() != null) {
            estrutura.setDescricao(request.getDescricao());
        }
        if (request.getEstruturaId() != null) {
            estrutura.setEstruturaId(request.getEstruturaId());
        }
        if (request.getEstruturaTipoId() != null) {
            estrutura.setEstruturaTipoId(request.getEstruturaTipoId());
        }
        if (request.getDataCriacao() != null) {
            estrutura.setDataCriacao(request.getDataCriacao());
        }
        if (request.getDataAlteracao() != null) {
            estrutura.setDataAlteracao(request.getDataAlteracao());
        }
        if (request.getAtivo() != null) {
            estrutura.setAtivo(request.getAtivo());
        }

        Estrutura atualizada = estruturaRepository.save(estrutura);
        return objectMapper.convertValue(atualizada, EstruturaResponseDTO.class);
    }

    @Transactional
    public void deletarEstrutura(Long id) {
        if (!estruturaRepository.existsById(id)) {
            throw new EntityNotFoundException("Estrutura não encontrada");
        }
        estruturaRepository.deleteById(id);
    }
}
