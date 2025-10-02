<<<<<<<< HEAD:src/main/java/org/example/scaneia_dsii/crud_usuario/repository/UsuarioRepository.java
package org.example.scaneia_dsii.crud_usuario.repository;

import org.example.scaneia_dsii.crud_usuario.model.Usuario;
========
package org.example.scaneia_dsii.repository;

import org.example.scaneia_dsii.model.Usuario;
>>>>>>>> origin:src/main/java/org/example/scaneia_dsii/repository/UsuarioRepository.java
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
}

