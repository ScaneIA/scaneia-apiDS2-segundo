package org.example.scaneia_dsii.repository;

public interface UsuarioTipoRepository {

    boolean ExistsById(Long id);
    boolean findAll();
    boolean findById(Long id);
    boolean findByAtivo(Boolean ativo);


}
