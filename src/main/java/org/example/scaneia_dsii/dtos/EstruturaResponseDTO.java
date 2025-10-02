package org.example.scaneia_dsii.dtos;

import java.util.Date;


public class EstruturaResponseDTO {

    private Integer id;
    private String descricao;
    private Integer estruturaId;
    private Integer estruturaTipoId;
    private Date dataCriacao;
    private Date dataAlteracao;
    private Boolean ativo;

    public EstruturaResponseDTO(String descricao, Integer estruturaId, Integer estruturaTipoId, Date dataCriacao, Date dataAlteracao, Boolean ativo) {
        this.descricao = descricao;
        this.estruturaId = estruturaId;
        this.estruturaTipoId = estruturaTipoId;
        this.dataCriacao = dataCriacao;
        this.dataAlteracao = dataAlteracao;
        this.ativo = ativo;
    }

    public EstruturaResponseDTO(){

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
