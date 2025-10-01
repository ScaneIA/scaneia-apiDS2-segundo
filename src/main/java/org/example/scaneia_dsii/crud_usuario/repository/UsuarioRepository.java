package org.example.scaneia_dsii.crud_usuario.repository;

import org.example.scaneia_dsii.crud_usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
}

