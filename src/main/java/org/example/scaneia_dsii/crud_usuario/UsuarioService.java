package org.example.scaneia_dsii.crud_usuario;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final ObjectMapper objectMapper;

    public UsuarioService(UsuarioRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    public List<UsuarioResponseDTO> listarTodos() {
        List<Usuario> usuarios = repository.findAll();

        if (usuarios.isEmpty()) {
            throw new RuntimeException("Não foi possível encontrar nenhum usuário");
        }

        return objectMapper.convertValue(usuarios, new TypeReference<List<UsuarioResponseDTO>>() {});
    }

    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario com ID " + id + " não encontrado."));

        return objectMapper.convertValue(usuario, UsuarioResponseDTO.class);
    }

    public UsuarioResponseDTO salvar(UsuarioRequestDTO dto) {
        if (repository.existsByCpf(dto.getCpf())) {
            throw new RuntimeException("CPF já cadastrado");
        }
        if (repository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }
        Usuario usuario = toEntity(dto);
        return toResponseDTO(repository.save(usuario));
    }

    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario com ID " + id + " não encontrado."));

        if (dto.getNome() != null) {
            usuario.setNome(dto.getNome());
        }
        if (dto.getCpf() != null) {
            usuario.setCpf(dto.getCpf());
        }
        if (dto.getSenha() != null) {
            usuario.setSenha(dto.getSenha());
        }
        if (dto.getEmail() != null) {
            usuario.setEmail(dto.getEmail());
        }
        if (dto.getIdUsuarioTipo() != null) {
            usuario.setIdUsuarioTipo(dto.getIdUsuarioTipo());
        }
        if (dto.getIdEstrutura() != null) {
            usuario.setIdEstrutura(dto.getIdEstrutura());
        }

        return toResponseDTO(repository.save(usuario));
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado");
        }
        repository.deleteById(id);
    }

    private Usuario toEntity(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setCpf(dto.getCpf());
        usuario.setSenha(dto.getSenha());
        usuario.setEmail(dto.getEmail());
        usuario.setIdUsuarioTipo(dto.getIdUsuarioTipo());
        usuario.setIdEstrutura(dto.getIdEstrutura());
        return usuario;
    }

    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getCpf(),
                usuario.getEmail(),
                usuario.getIdUsuarioTipo(),
                usuario.getIdEstrutura(),
                usuario.getDataCriacao()
        );
    }
}

