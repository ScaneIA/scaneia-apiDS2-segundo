package org.example.scaneia_dsii.repository;

import org.example.scaneia_dsii.model.UsuarioAcessoLogDau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioAcessoLogDauRepository extends JpaRepository<UsuarioAcessoLogDau, Long> {
    Optional<UsuarioAcessoLogDau> findByIdUsuario(Long IdUsuario);

    @Query("SELECT l FROM UsuarioAcessoLogDau l WHERE l.idUsuario = :IdUsuario ORDER BY l.dataAcesso DESC LIMIT 1")
    Optional<UsuarioAcessoLogDau> findUltimoAcessoPorUsuario(@Param("IdUsuario") Long IdUsuario);

}


