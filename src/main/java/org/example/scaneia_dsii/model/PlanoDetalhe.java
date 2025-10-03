package org.example.scaneia_dsii.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Getter
@Setter
public class PlanoDetalhe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    Double preco;

    @Column(name = "qtd_meses", nullable = false)
    Integer qtdMeses;

    @Column(name = "id_plano", nullable = false)
    Integer planoId;

    @Column(name = "data_criacao", nullable = false)
    Date dataCriacao;

    public PlanoDetalhe(Double preco, Integer qtdMeses, Integer planoId, Date dataCriacao) {
        this.preco = preco;
        this.qtdMeses = qtdMeses;
        this.planoId = planoId;
        this.dataCriacao = dataCriacao;
    }
    public PlanoDetalhe(){

    }
}
