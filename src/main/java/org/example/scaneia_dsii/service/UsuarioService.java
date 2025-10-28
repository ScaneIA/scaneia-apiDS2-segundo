package org.example.scaneia_dsii.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Getter;
import org.example.scaneia_dsii.dtos.UsuarioPerfilResponseDTO;
import org.example.scaneia_dsii.model.Usuario;
import org.example.scaneia_dsii.model.UsuarioTipo;
import org.example.scaneia_dsii.repository.UsuarioRepository;
import org.example.scaneia_dsii.dtos.UsuarioRequestDTO;
import org.example.scaneia_dsii.dtos.UsuarioResponseDTO;
import org.example.scaneia_dsii.repository.UsuarioTipoRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final UsuarioTipoRepository usuarioTipoRepository;
    private final ObjectMapper objectMapper;
    @Getter
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final JwtService jwtService;


    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioTipoRepository usuarioTipoRepository, ObjectMapper objectMapper, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioTipoRepository = usuarioTipoRepository;
        this.objectMapper = objectMapper;
        this.jwtService = jwtService;
    }
    public UsuarioResponseDTO inserirUsuario (UsuarioRequestDTO request) {
        if (usuarioRepository.existsByCpf(request.getCpf())) {
            throw new RuntimeException("CPF já cadastrado"); //Excessao personalizada
        }
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email já cadastrado"); //Excessao personalizada
        }
        Usuario novoUsuario = objectMapper.convertValue(request, Usuario.class);
        novoUsuario.setSenha(passwordEncoder.encode(request.getSenha()));
        novoUsuario.setDataCriacao(new Date());
        usuarioRepository.save(novoUsuario);
        return objectMapper.convertValue(novoUsuario, UsuarioResponseDTO.class);
    }

    public List<UsuarioResponseDTO> listarUsuarios(){
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioResponseDTO> response = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            response.add(objectMapper.convertValue(usuario, UsuarioResponseDTO.class));
        }
        if (response.isEmpty()){
            throw new EntityNotFoundException("Nenhum usuário cadastrado no momento");
        }
        return response;
    }

    public UsuarioResponseDTO listarUsuarioPorId(Long id){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com o ID" + id + "não encontrado"));
        return objectMapper.convertValue(usuario, UsuarioResponseDTO.class);
    }

    @Transactional
    public UsuarioResponseDTO atualizarUsuarioParcial(Long id, UsuarioRequestDTO request){
        //Temos que ver certinho as excessões que podem dar, cpf... email e tudo mais
        if (!usuarioRepository.existsById(id)){
            throw new EntityNotFoundException("Usuário com o ID " + id + " não encontrado");
        }
        Optional<Usuario> usuarioEncontrado = usuarioRepository.findById(id);
        Usuario usuario = usuarioEncontrado.get();
        if (request.getNome() != null) {
            usuario.setNome(request.getNome());
        }
        if (request.getCpf() != null) {
            usuario.setCpf(request.getCpf());
        }
        if (request.getSenha() != null) {
            usuario.setSenha(request.getSenha());
        }
        if (request.getEmail() != null) {
            usuario.setEmail(request.getEmail());
        }
        if (request.getIdUsuarioTipo() != null) {
            usuario.setIdUsuarioTipo(request.getIdUsuarioTipo());
        }
        if (request.getIdEstrutura() != null) {
            usuario.setIdEstrutura(request.getIdEstrutura());
        }
        usuarioRepository.save(usuario);
        return objectMapper.convertValue(usuario, UsuarioResponseDTO.class);
    }
    @Transactional
    public void deletarUsuario(Long id){
        if (!usuarioRepository.existsById(id)){
            throw new EntityNotFoundException("Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    public UsuarioPerfilResponseDTO filtrarInformacoesUsuario(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String username = jwtService.extrairUsername(token);

        if (!usuarioRepository.existsByEmail(username)) {
            throw new EntityNotFoundException("Usuário não encontrado");
        }
        return usuarioRepository.filtrarInformacoesUsuarios(username);
    }

    public void validarCredenciais(String email, String password) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(password, usuario.getSenha())) {
            System.out.println("Senha incorreta para o usuário: " + email);
            throw new RuntimeException("Senha inválida");
        }

        System.out.println("Usuário autenticado com sucesso: " + email);
    }

    public String recuperarTipoUsuario(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        UsuarioTipo usuarioTipo = usuarioTipoRepository.findById(usuario.getIdUsuarioTipo()).get();
        return usuarioTipo.getDescricao();
    }



}

