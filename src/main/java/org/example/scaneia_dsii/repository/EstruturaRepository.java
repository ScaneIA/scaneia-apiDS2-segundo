package org.example.scaneia_dsii.repository;

import java.util.Date;

public interface EstruturaRepository {

    boolean ExistsByDataCriacaoAndDescricao(String dataCriacao, String descricao);

}
