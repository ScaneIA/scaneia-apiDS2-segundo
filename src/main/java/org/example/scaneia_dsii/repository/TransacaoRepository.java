package org.example.scaneia_dsii.repository;

import java.util.Date;

public interface TransacaoRepository {

    boolean ExistsById(Long id);
    boolean findAll();
    boolean findById(Long id);
    boolean findByDataCriacao(Date dataCriacao);
    boolean findByDataAlteracao(Date dataAlteracao);


}
