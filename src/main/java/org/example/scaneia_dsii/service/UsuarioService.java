package org.example.scaneia_dsii.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Getter;
import org.example.scaneia_dsii.dtos.UsuarioPerfilResponseDTO;
import org.example.scaneia_dsii.model.Usuario;
import org.example.scaneia_dsii.model.UsuarioHierarquiaProjection;
import org.example.scaneia_dsii.model.UsuarioTipo;
import org.example.scaneia_dsii.repository.UsuarioRepository;
import org.example.scaneia_dsii.dtos.UsuarioRequestDTO;
import org.example.scaneia_dsii.dtos.UsuarioResponseDTO;
import org.example.scaneia_dsii.repository.UsuarioTipoRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.*;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final UsuarioTipoRepository usuarioTipoRepository;
    private final ObjectMapper objectMapper;
    @Getter
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioTipoRepository usuarioTipoRepository, ObjectMapper objectMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioTipoRepository = usuarioTipoRepository;
        this.objectMapper = objectMapper;
    }
    public List<UsuarioHierarquiaProjection> listarHierarquia(Long idUsuario, String orientacao) {
        return usuarioRepository.buscarUsuariosHierarquia(idUsuario, orientacao);
    }

    public UsuarioResponseDTO inserirUsuario (UsuarioRequestDTO request) {
        if (usuarioRepository.existsByCpf(request.getCpf())) {
            throw new RuntimeException("CPF já cadastrado"); //Excessao personalizada
        }
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email já cadastrado"); //Excessao personalizada                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      org.checkerframework.checker.units.qual.C |q
        }
        Usuario novoUsuario = objectMapper.convertValue(request, Usuario.class);
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

    public UsuarioPerfilResponseDTO filtrarInformacoesUsuario(String username) {
        if (!usuarioRepository.existsByEmail(username)) {
            throw new EntityNotFoundException("Usuário não encontrado");
        }
        return usuarioRepository.filtrarInformacoesUsuarios(username);
    }


    public void validarCredenciais(String email, String password) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado");
        }


        if (!passwordEncoder.matches(password, usuario.getSenha())) {
            System.out.println("Senha incorreta para o usuário: " + email);
            throw new RuntimeException("Senha inválida");
        }

        System.out.println("Usuário autenticado com sucesso: " + email);
    }

    public String recuperarTipoUsuario(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado");
        }
        UsuarioTipo usuarioTipo = usuarioTipoRepository.findById(usuario.getIdUsuarioTipo()).get();
        return usuarioTipo.getDescricao();
    }

    public Map<String, Object> recuperarIds(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado");
        }

        String username = usuario.getEmail();
        Long idUsuario = usuario.getId();
        Long idEstrutura = usuario.getIdEstrutura();
        Long idTipoUsuario = usuario.getIdUsuarioTipo();

        Map<String, Object> map = new HashMap<>();

        map.put("id_usuario", idUsuario);
        map.put("id_estrutura", idEstrutura);
        map.put("id_tipo_usuario", idTipoUsuario);
        map.put("username", username);

        return map;
    }

    @Transactional
    public UsuarioResponseDTO atualizarFotoPorEmail(String email, String urlPhoto) {
        Usuario usuario = usuarioRepository.findByEmail(email);

        if (usuario == null) {
            throw new EntityNotFoundException("Usuário com email " + email + " não encontrado");
        }

        usuario.setUrlPhoto(urlPhoto);
        usuarioRepository.save(usuario);

        return objectMapper.convertValue(usuario, UsuarioResponseDTO.class);
    }

}

