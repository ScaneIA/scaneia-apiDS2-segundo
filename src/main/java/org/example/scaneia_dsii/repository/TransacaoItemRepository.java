package org.example.scaneia_dsii.repository;

public interface TransacaoItemRepository {

    boolean ExistsById(Long id);
    boolean findAll();
    boolean findById(Long id);
    boolean findByIdTransacao(Long id);
    boolean findByIdPlanoEspecificado(Long id);
}
