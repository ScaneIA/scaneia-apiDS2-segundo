package org.example.scaneia_dsii.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "estrutura")
@Setter
@Getter
public class Estrutura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "estrutura_id")
    private Integer estruturaId;

    @Column(name = "estrutura_tipo_id")
    private Integer estruturaTipoId;

    @Column(name = "data_criacao")
    private Date dataCriacao;

    @Column(name = "data_alteracao")
    private Date dataAlteracao;

    @Column(name = "ativo")
    private Boolean ativo;

}
