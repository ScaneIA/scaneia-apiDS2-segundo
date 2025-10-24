package org.example.scaneia_dsii.repository;

import org.example.scaneia_dsii.model.UsuarioTipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioTipoRepository extends JpaRepository<UsuarioTipo, Long> {

    List<UsuarioTipo> findByAtivo(Boolean ativo);
}
