package org.example.scaneia_dsii.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuario_tipo")
@Setter
@Getter
public class UsuarioTipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "descricao")
    String descricao;

    @Column(name = "ativo")
    Boolean ativo;

    public UsuarioTipo(String descricao, Boolean ativo) {
        this.descricao = descricao;
        this.ativo = ativo;
    }

    public UsuarioTipo(){

    }
}
