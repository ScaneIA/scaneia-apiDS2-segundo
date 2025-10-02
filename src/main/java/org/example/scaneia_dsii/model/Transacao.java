package org.example.scaneia_dsii.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Entity
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "valor_total")
    @NotNull
    Double valorTotal;

    @NotNull
    @Column(name = "estrutura_id")
    Integer estrutura_id;

    @NotNull
    @Column(name = "data_criacao")
    Date dataCriacao;

    @NotNull
    @Column(name = "data_alteracao")
    Date dataAlteracao;


    public Transacao(Double valorTotal, Integer estrutura_id, Date dataCriacao, Date dataAlteracao) {
        this.valorTotal = valorTotal;
        this.estrutura_id = estrutura_id;
        this.dataCriacao = dataCriacao;
        this.dataAlteracao = dataAlteracao;
    }

    public Transacao(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Integer getEstrutura_id() {
        return estrutura_id;
    }

    public void setEstrutura_id(Integer estrutura_id) {
        this.estrutura_id = estrutura_id;
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
}
