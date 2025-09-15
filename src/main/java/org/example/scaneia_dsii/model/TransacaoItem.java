package org.example.scaneia_dsii.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class TransacaoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "qtd_plano")
    @NotNull
    Integer qtdPlano;

    @Column(name = "id_transacao")
    @NotNull
    Integer idTransacao;

    @Column(name = "id_plano_especificao")
    @NotNull
    Integer idPlanoEspecificao;

    public TransacaoItem(Integer qtdPlano, Integer idTransacao, Integer idPlanoEspecificao) {
        this.qtdPlano = qtdPlano;
        this.idTransacao = idTransacao;
        this.idPlanoEspecificao = idPlanoEspecificao;
    }

    public TransacaoItem(){

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQtdPlano() {
        return qtdPlano;
    }

    public void setQtdPlano(Integer qtdPlano) {
        this.qtdPlano = qtdPlano;
    }

    public Integer getIdTransacao() {
        return idTransacao;
    }

    public void setIdTransacao(Integer idTransacao) {
        this.idTransacao = idTransacao;
    }

    public Integer getIdPlanoEspecificao() {
        return idPlanoEspecificao;
    }

    public void setIdPlanoEspecificao(Integer idPlanoEspecificao) {
        this.idPlanoEspecificao = idPlanoEspecificao;
    }
}
