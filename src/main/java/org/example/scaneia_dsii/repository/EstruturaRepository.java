package org.example.scaneia_dsii.repository;

import java.util.Date;

public interface EstruturaRepository {

    boolean ExistsByDataCriacaoAndDescricao(String dataCriacao, String descricao);
    boolean ExistsById(Long id);
    boolean findAll();
    boolean findById(Long id);
    boolean findDataCriacao(Date dataCriacao);
    boolean findByDataAlteracao(Date dataAlteracao);

}
