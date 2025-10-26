package org.example.scaneia_dsii.repository;

import org.example.scaneia_dsii.model.UsuarioTipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.file.LinkOption;
import java.util.Optional;

public interface UsuarioTipoRepository extends JpaRepository<UsuarioTipo, Long> {

}
