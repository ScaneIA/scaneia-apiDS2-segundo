package org.example.scaneia_dsii.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Plano {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false, length = 255)
    String descricao;

    @Column(name = "qtd_planilhas", nullable = false)
    Integer qtdPlanilhas;

    @Column(name = "data_criacao", nullable = false)
    Date dataCriacao;

    @Column(name = "data_alteracao", nullable = false)
    Date dataAlteracao;

    @Column(nullable = false)
    Boolean ativo;

    public Plano(String descricao, Integer qtdPlanilhas, Date dataCriacao, Date dataAlteracao, Boolean ativo) {
        this.descricao = descricao;
        this.qtdPlanilhas = qtdPlanilhas;
        this.dataCriacao = dataCriacao;
        this.dataAlteracao = dataAlteracao;
        this.ativo = ativo;
    }

    public Plano(){
    }
}
