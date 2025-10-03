package org.example.scaneia_dsii.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.scaneia_dsii.dtos.PlanoDetalheRequestDTO;
import org.example.scaneia_dsii.dtos.PlanoDetalheResponseDTO;
import org.example.scaneia_dsii.model.PlanoDetalhe;
import org.example.scaneia_dsii.repository.PlanoDetalheRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PlanoDetalheService {

    private final PlanoDetalheRepository planoDetalheRepository;
    private final ObjectMapper objectMapper;

    public PlanoDetalheService(PlanoDetalheRepository planoDetalheRepository, ObjectMapper objectMapper) {
        this.planoDetalheRepository = planoDetalheRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public PlanoDetalheResponseDTO inserirPlanoDetalhe(PlanoDetalheRequestDTO request){
        PlanoDetalhe novoPlanoDetalhe = objectMapper.convertValue(request, PlanoDetalhe.class);
        novoPlanoDetalhe.setDataCriacao(new Date());
        planoDetalheRepository.save(novoPlanoDetalhe);
        return objectMapper.convertValue(novoPlanoDetalhe, PlanoDetalheResponseDTO.class);
    }

    public List<PlanoDetalheResponseDTO> listarPlanosDetalhes(){
        List<PlanoDetalhe> planosDetalhes = planoDetalheRepository.findAll();
        List<PlanoDetalheResponseDTO> response = new ArrayList<>();
        for (PlanoDetalhe planoDetalhe : planosDetalhes) {
            response.add(objectMapper.convertValue(planoDetalhe, PlanoDetalheResponseDTO.class));
        }
        if (response.isEmpty()){
            throw new EntityNotFoundException("Nenhum plano detalhe cadastrado no momento");
        }
        return response;
    }
    public PlanoDetalheResponseDTO listarPlanoDetalhePorId(Long id){
        PlanoDetalhe planoDetalhe = planoDetalheRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plano detalhe não encontrado"));
        return objectMapper.convertValue(planoDetalhe, PlanoDetalheResponseDTO.class);
    }
    @Transactional
    public PlanoDetalheResponseDTO atualizarPlanoDetalheParcial(Long id, PlanoDetalheRequestDTO request){
        if (!planoDetalheRepository.existsById(id)){
            throw new EntityNotFoundException("Plano detalhe não encontrado");
        }
        Optional<PlanoDetalhe> planoDetalheEncontrado = planoDetalheRepository.findById(id);
        PlanoDetalhe planoDetalhe = planoDetalheEncontrado.get();
        if (request.getPreco() != null){
            planoDetalhe.setPreco(request.getPreco());
        }
        if (request.getQtdMeses() != null){
            planoDetalhe.setQtdMeses(request.getQtdMeses());
        }
        if (request.getPlanoId() != null){
            planoDetalhe.setPlanoId(request.getPlanoId());
        }
        planoDetalheRepository.save(planoDetalhe);
        return objectMapper.convertValue(planoDetalhe, PlanoDetalheResponseDTO.class);
    }
    @Transactional
    public void deletarPlanoDetalhe(Long id){
        if (!planoDetalheRepository.existsById(id)){
            throw new EntityNotFoundException("Plano detalhe não encontrado");
        }
        planoDetalheRepository.deleteById(id);
    }


}
