package org.example.scaneia_dsii.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.scaneia_dsii.dtos.UsuarioTipoRequestDTO;
import org.example.scaneia_dsii.dtos.UsuarioTipoResponseDTO;
import org.example.scaneia_dsii.model.UsuarioTipo;
import org.example.scaneia_dsii.repository.UsuarioTipoRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioTipoService {

    private final UsuarioTipoRepository usuarioTipoRepository;
    private final ObjectMapper objectMapper;

    public UsuarioTipoService(UsuarioTipoRepository usuarioTipoRepository, ObjectMapper objectMapper) {
        this.usuarioTipoRepository = usuarioTipoRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public UsuarioTipoResponseDTO inserirUsuarioTipo(UsuarioTipoRequestDTO request) {
        UsuarioTipo novoUsuarioTipo = objectMapper.convertValue(request, UsuarioTipo.class);

        if (novoUsuarioTipo.getDescricao() == null) {
            novoUsuarioTipo.setDescricao("");
        }
        if (novoUsuarioTipo.getAtivo() == null) {
            novoUsuarioTipo.setAtivo(true);
        }

        usuarioTipoRepository.save(novoUsuarioTipo);
        return objectMapper.convertValue(novoUsuarioTipo, UsuarioTipoResponseDTO.class);
    }

    public List<UsuarioTipoResponseDTO> listarUsuarioTipos() {
        List<UsuarioTipo> usuarioTipos = usuarioTipoRepository.findAll();
        if (usuarioTipos.isEmpty()) {
            throw new EntityNotFoundException("Nenhum tipo de usuário cadastrado no momento");
        }

        List<UsuarioTipoResponseDTO> response = new ArrayList<>();
        for (UsuarioTipo usuarioTipo : usuarioTipos) {
            response.add(objectMapper.convertValue(usuarioTipo, UsuarioTipoResponseDTO.class));
        }
        return response;
    }

    public UsuarioTipoResponseDTO listarUsuarioTipoPorId(Long id) {
        UsuarioTipo usuarioTipo = usuarioTipoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de usuário não encontrado"));
        return objectMapper.convertValue(usuarioTipo, UsuarioTipoResponseDTO.class);
    }

    @Transactional
    public UsuarioTipoResponseDTO atualizarUsuarioTipoParcial(Long id, UsuarioTipoRequestDTO request) {
        UsuarioTipo usuarioTipo = usuarioTipoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de usuário não encontrado"));

        if (request.getDescricao() != null) {
            usuarioTipo.setDescricao(request.getDescricao());
        }
        if (request.getAtivo() != null) {
            usuarioTipo.setAtivo(request.getAtivo());
        }

        usuarioTipoRepository.save(usuarioTipo);
        return objectMapper.convertValue(usuarioTipo, UsuarioTipoResponseDTO.class);
    }

    @Transactional
    public void deletarUsuarioTipo(Long id) {
        if (!usuarioTipoRepository.existsById(id)) {
            throw new EmptyResultDataAccessException(1);
        }
        usuarioTipoRepository.deleteById(id);
    }
}
