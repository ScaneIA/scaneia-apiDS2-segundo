package org.example.scaneia_dsii.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Entity
public class Servicos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "transacao_id")
    @NotNull
    Integer transacaoId;

    @Column(name = "data_inicio")
    @NotNull
    Date dataInicio;

    @NotNull
    @Column(name = "data_fim")
    Date dataFim;

    public Servicos(Integer transacaoId, Date dataInicio, Date dataFim) {
        this.transacaoId = transacaoId;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public Servicos(){

    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTransacaoId() {
        return transacaoId;
    }

    public void setTransacaoId(Integer transacaoId) {
        this.transacaoId = transacaoId;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }
}
