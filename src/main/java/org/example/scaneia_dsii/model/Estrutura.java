package org.example.scaneia_dsii.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Estrutura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String descricao;

    @Column(name = "estrutura_id")
    private Integer estruturaId;


    @NotNull
    @Column(name = "estrutura_tipo_id")
    private Integer estruturaTipoId;

    @NotNull
    @Column(name = "data_criacao")
    private Date dataCriacao;

    @NotNull
    @Column(name = "data_alteracao")
    private Date dataAlteracao;

    @NotNull
    private Boolean ativo;

    public Estrutura(String descricao, Integer estruturaId, Integer estruturaTipoId, Date dataCriacao, Date dataAlteracao, Boolean ativo) {
        this.descricao = descricao;
        this.estruturaId = estruturaId;
        this.estruturaTipoId = estruturaTipoId;
        this.dataCriacao = dataCriacao;
        this.dataAlteracao = dataAlteracao;
        this.ativo = ativo;
    }

    public Estrutura(){

    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getEstruturaId() {
        return estruturaId;
    }

    public void setEstruturaId(Integer estruturaId) {
        this.estruturaId = estruturaId;
    }

    public Integer getEstruturaTipoId() {
        return estruturaTipoId;
    }

    public void setEstruturaTipoId(Integer estruturaTipoId) {
        this.estruturaTipoId = estruturaTipoId;
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
