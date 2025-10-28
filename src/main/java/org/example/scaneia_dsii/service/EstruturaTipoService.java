package org.example.scaneia_dsii.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.scaneia_dsii.dtos.EstruturaTipoRequestDTO;
import org.example.scaneia_dsii.dtos.EstruturaTipoResponseDTO;
import org.example.scaneia_dsii.model.EstruturaTipo;
import org.example.scaneia_dsii.repository.EstruturaTipoRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EstruturaTipoService {

    private final EstruturaTipoRepository estruturaTipoRepository;
    private final ObjectMapper objectMapper;
    public EstruturaTipoService(EstruturaTipoRepository estruturaTipoRepository, ObjectMapper objectMapper) {
        this.estruturaTipoRepository = estruturaTipoRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public EstruturaTipoResponseDTO inserirEstruturaTipo(EstruturaTipoRequestDTO request){
        EstruturaTipo novaEstruturaTipo = objectMapper.convertValue(request, EstruturaTipo.class);
        estruturaTipoRepository.save(novaEstruturaTipo);
        return objectMapper.convertValue(novaEstruturaTipo, EstruturaTipoResponseDTO.class);
    }

    public List<EstruturaTipoResponseDTO> listarEstruturasTipo(){
        List<EstruturaTipo> estruturasTipo = estruturaTipoRepository.findAll();
        List<EstruturaTipoResponseDTO> response = new ArrayList<>();
        for (EstruturaTipo estruturaTipo : estruturasTipo) {
            response.add(objectMapper.convertValue(estruturaTipo, EstruturaTipoResponseDTO.class));
        }
        if (response.isEmpty()){
            throw new EntityNotFoundException("Nenhum tipo de estrutura cadastrado no momento");
        }
        return response;
    }
    public EstruturaTipoResponseDTO listarEstruturaTipoPorId(Integer id){
        EstruturaTipo estruturaTipo = estruturaTipoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de estrutura não encontrado"));
        return objectMapper.convertValue(estruturaTipo, EstruturaTipoResponseDTO.class);
    }
    @Transactional
    public EstruturaTipoResponseDTO atualizarEstruturaTipoParcial(Integer id, EstruturaTipoRequestDTO request){
        if (!estruturaTipoRepository.existsById(id)){
            throw new EntityNotFoundException("Tipo de estrutura não encontrado");
        }
        Optional<EstruturaTipo> estruturaTipoEncontrado = estruturaTipoRepository.findById(id);
        EstruturaTipo estruturaTipo = estruturaTipoEncontrado.get();
        if (request.getOrdem() != null){
            estruturaTipo.setOrdem(request.getOrdem());
        }
        if (request.getDescricao() != null){
            estruturaTipo.setDescricao(request.getDescricao());
        }
        if (request.getAtivo() != null){
            estruturaTipo.setAtivo(request.getAtivo());
        }
        estruturaTipoRepository.save(estruturaTipo);
        return objectMapper.convertValue(estruturaTipo, EstruturaTipoResponseDTO.class);
    }
    @Transactional
    public void deletarEstruturaTipo(Integer id){
        if (!estruturaTipoRepository.existsById(id)){
            throw new EntityNotFoundException("Tipo de estrutura não encontrado");
        }
        estruturaTipoRepository.deleteById(id);
    }

}
