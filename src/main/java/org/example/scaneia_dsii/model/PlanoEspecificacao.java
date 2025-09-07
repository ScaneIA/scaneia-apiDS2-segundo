package org.example.scaneia_dsii.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import javax.print.attribute.standard.MediaSize;
import java.util.Date;

@Entity
public class PlanoEspecificacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotNull
    Double preco;

    @NotNull
    @Column(name = "qtd_meses")
    Integer qtdMeses;

    @NotNull
    @Column(name = "plano_id")
    Integer planoId;

    @NotNull
    @Column(name = "data_criacao")
    Date dataCriacao;

    public PlanoEspecificacao(Double preco, Integer qtdMeses, Integer planoId, Date dataCriacao) {
        this.preco = preco;
        this.qtdMeses = qtdMeses;
        this.planoId = planoId;
        this.dataCriacao = dataCriacao;
    }

    public PlanoEspecificacao(){

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getQtdMeses() {
        return qtdMeses;
    }

    public void setQtdMeses(Integer qtdMeses) {
        this.qtdMeses = qtdMeses;
    }

    public Integer getPlanoId() {
        return planoId;
    }

    public void setPlanoId(Integer planoId) {
        this.planoId = planoId;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
