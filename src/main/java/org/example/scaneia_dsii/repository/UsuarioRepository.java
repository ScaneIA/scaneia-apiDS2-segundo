package org.example.scaneia_dsii.repository;

import org.example.scaneia_dsii.dtos.UsuarioPerfilResponseDTO;
import org.example.scaneia_dsii.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    Optional<Usuario> findByEmail(String email);
//    @Query("select u.nome, u.email, u.dataCriacao, u.cpf from Usuario u where u.id = :id")
//    UsuarioPerfilResponseDTO filtrarInformacoesUsuarios(@Param("id") Long id);

    @Query("select new org.example.scaneia_dsii.dtos.UsuarioPerfilResponseDTO(u.nome, u.email, u.dataCriacao, u.cpf)" +  "from Usuario u where u.email = :username")
    UsuarioPerfilResponseDTO filtrarInformacoesUsuarios(@Param("username") String username);


}

