package org.example.scaneia_dsii.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
public class Plano {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotNull
    @Size(max = 255)
    String descricao;

    @NotNull
    @Column(name = "qtd_planilhas")
    Integer qtdPlanilhas;

    @NotNull
    @Column(name = "data_criacao")
    Date dataCriacao;

    @NotNull
    @Column(name = "data_alteracao")
    Date dataAlteracao;

    @NotNull
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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQtdPlanilhas() {
        return qtdPlanilhas;
    }

    public void setQtdPlanilhas(Integer qtdPlanilhas) {
        this.qtdPlanilhas = qtdPlanilhas;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
