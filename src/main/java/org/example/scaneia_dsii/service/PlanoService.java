package org.example.scaneia_dsii.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.scaneia_dsii.dtos.PlanoRequestDTO;
import org.example.scaneia_dsii.dtos.PlanoResponseDTO;
import org.example.scaneia_dsii.model.Plano;
import org.example.scaneia_dsii.repository.PlanoRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlanoService {
    private final PlanoRepository planoRepository;
    private final ObjectMapper objectMapper;
    public PlanoService(PlanoRepository planoRepository, ObjectMapper objectMapper) {
        this.planoRepository = planoRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public PlanoResponseDTO inserirPlano(PlanoRequestDTO request){
        Plano novoProduto = objectMapper.convertValue(request, Plano.class);
        novoProduto.setDataCriacao(new Date());
        novoProduto.setDataAlteracao(new Date());
        planoRepository.save(novoProduto);
        return objectMapper.convertValue(novoProduto, PlanoResponseDTO.class);
    }

    public List<PlanoResponseDTO> listarPlanos(){
        List<Plano> planos = planoRepository.findAll();
        List<PlanoResponseDTO> response = new ArrayList<>();
        for (Plano plano : planos) {
            response.add(objectMapper.convertValue(plano, PlanoResponseDTO.class));
        }
        if (response.isEmpty()){
            throw new EntityNotFoundException("Nenhum plano cadastrado no momento");
        }
        return response;
    }
    public PlanoResponseDTO listarPlanoPorId(Integer id){
        Plano plano = planoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plano não encontrado"));
        return objectMapper.convertValue(plano, PlanoResponseDTO.class);
    }
    @Transactional
    public PlanoResponseDTO atualizarPlanoParcial(Integer id, PlanoRequestDTO request){
        if (!planoRepository.existsById(id)){
            throw new EntityNotFoundException("Plano não encontrado");
        }
        Optional<Plano> planoEncontrado = planoRepository.findById(id);
        Plano plano = planoEncontrado.get();
        if (request.getDescricao() != null){
            plano.setDescricao(request.getDescricao());
        }
        if (request.getQtdPlanilhas() != null){
            plano.setQtdPlanilhas(request.getQtdPlanilhas());
        }
        if (request.getAtivo() != null){
            plano.setAtivo(request.getAtivo());
        }
        plano.setDataAlteracao(new Date());
        planoRepository.save(plano);
        return objectMapper.convertValue(plano, PlanoResponseDTO.class);
    }
    @Transactional
    public void deletarPlano(Integer id){
        if (!planoRepository.existsById(id)){
            throw new EntityNotFoundException("Plano não encontrado");
        }
        planoRepository.deleteById(id);
    }
}
